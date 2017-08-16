package at.tuwien.monitoring.client.aspect;

import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.activemq.ActiveMQConnection;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.ClientResponse;

import at.tuwien.common.GlobalConstants;
import at.tuwien.common.Method;
import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.jms.JmsSenderService;
import at.tuwien.monitoring.jms.messages.ClientResponseTimeMessage;
import at.tuwien.monitoring.jms.messages.MetricAggregationMessage;

@Aspect
public class RequestAspect {

	private final static Logger logger = Logger.getLogger(RequestAspect.class);

	private JmsSenderService jmsService;
	private String publicIPAddress;

	private RequestAspect() {
		publicIPAddress = Utils.lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				if (jmsService != null) {
					jmsService.stop();
				}
			}
		}));
	}

	@Pointcut("execution(* main(..))")
	public void mainExecution() {
	}

	@Before("mainExecution()")
	public void beforeMain(JoinPoint joinPoint) {
		// retrieve config file path from main arguments
		Object[] args = joinPoint.getArgs();
		String[] stringArgs = (String[]) args[0];

		String brokerUrl = ActiveMQConnection.DEFAULT_BROKER_URL;

		for (String arg : stringArgs) {
			if (!arg.startsWith("config:")) {
				continue;
			}
			String split[] = arg.split("config:");
			if (split.length != 2) {
				continue;
			}
			Settings settings = Utils.readProperties(split[1]);
			brokerUrl = settings.brokerUrl;
		}

		jmsService = new JmsSenderService(brokerUrl, GlobalConstants.QUEUE_CLIENTS);
		jmsService.start();
	}

	@Pointcut("execution(* _apply(..))")
	public void applyExecution() {
	}

	@Around("applyExecution()")
	public Object applyAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Throwable ex = null;
		Object response = null;
		int responseCode = -1;

		long startTime = System.nanoTime();

		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			ex = throwable;
		}

		long responseTime = System.nanoTime() - startTime;

		if (ex != null) {
			// error -> response was not successful, assume http error code 500
			// set invalid response time
			responseCode = HttpURLConnection.HTTP_UNAVAILABLE;
			responseTime = -1;
		} else if (response instanceof ClientResponse) {
			ClientResponse clientResponse = (ClientResponse) response;
			responseCode = clientResponse.getStatus();
		}

		Object arg = proceedingJoinPoint.getArgs()[0];
		if (arg instanceof ClientRequest) {
			ClientRequest clientRequest = (ClientRequest) arg;
			sendReponseTime(clientRequest.getUri().toURL().toString(), Method.valueOf(clientRequest.getMethod()),
					responseTime, responseCode);
		}

		if (ex != null) {
			throw ex;
		}

		return response;
	}

	@Pointcut("@annotation(MonitorRequest)")
	public void hasMonitorRequestAnnotation() {
	}

	@Pointcut("execution(* *(..))")
	public void atExecution() {
	}

	@Around("hasMonitorRequestAnnotation() && atExecution()")
	public Object annotationRequest(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Throwable ex = null;
		Object response = null;
		int responseCode = HttpURLConnection.HTTP_OK;

		long startTime = System.nanoTime();

		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			ex = throwable;
		}

		long responseTime = System.nanoTime() - startTime;

		if (ex != null) {
			// error -> response was not successful, assume http error code 500, set
			// invalid response time
			responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
			responseTime = -1;
		}

		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Annotation[] annotations = signature.getMethod().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof MonitorRequest) {
				MonitorRequest monitorRequest = (MonitorRequest) annotation;
				sendReponseTime(monitorRequest.target(), monitorRequest.method(), responseTime, responseCode);
				break;
			}
		}

		if (ex != null) {
			throw ex;
		}

		return response;
	}

	public void sendReponseTime(final String target, final Method method, final long responseTime,
			final int responseCode) {
		if (jmsService.isConnected()) {
			MetricAggregationMessage metricAggregationMessage = new MetricAggregationMessage();
			metricAggregationMessage.addMetricMessage(new ClientResponseTimeMessage(publicIPAddress, new Date(), target,
					method, responseTime == -1 ? -1 : TimeUnit.NANOSECONDS.toMillis(responseTime), responseCode));
			jmsService.sendObjectMessage(metricAggregationMessage);
		}
	}
}