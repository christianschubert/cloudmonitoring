package at.tuwien.monitoring.client.aspect;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import at.tuwien.common.GlobalConstants;
import at.tuwien.common.Method;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.client.aspect.constants.Constants;
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
		jmsService = new JmsSenderService(Constants.brokerURL, GlobalConstants.QUEUE_CLIENTS);
		jmsService.start();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				jmsService.stop();
			}
		}));
	}

	@Pointcut("@annotation(MonitorRequest)")
	public void hasMonitorRequestAnnotation() {
	}

	@Pointcut("call (* java.net.HttpURLConnection.getResponseCode(..))")
	public void callGetResonseCode() {
	}

	@Pointcut("!within(RequestAspect)")
	public void notThisAspect() {
	}

	@Pointcut("execution(* *(..))")
	public void atExecution() {
	}

	@Around("hasMonitorRequestAnnotation() && atExecution()")
	public Object annotationRequest(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Throwable ex = null;
		int responseCode = HttpURLConnection.HTTP_OK;

		long startTime = System.currentTimeMillis();

		Object response = null;
		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			// error -> response was not successful, assume http error code 500
			ex = throwable;
			responseCode = HttpURLConnection.HTTP_INTERNAL_ERROR;
		}

		long responseTime = System.currentTimeMillis() - startTime;

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

	@Around("callGetResonseCode() && notThisAspect()")
	public Object callRequest(final ProceedingJoinPoint proceedingJoinPoint) {

		long startTime = System.currentTimeMillis();

		Object response = null;
		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return response;
		}

		long responseTime = System.currentTimeMillis() - startTime;

		Object target = proceedingJoinPoint.getTarget();
		if (target instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) target;

			int responseCode = -1;
			try {
				responseCode = httpURLConnection.getResponseCode();
			} catch (IOException e) {
				logger.error("Cannot acquire response code. Ignoring code.");
			}

			sendReponseTime(httpURLConnection.getURL().toString(), Method.valueOf(httpURLConnection.getRequestMethod()),
					responseTime, responseCode);
		}

		return response;
	}

	public void sendReponseTime(final String target, final Method method, final long responseTime,
			final int responseCode) {
		if (jmsService.isConnected()) {
			MetricAggregationMessage metricAggregationMessage = new MetricAggregationMessage();
			metricAggregationMessage.addMetricMessage(new ClientResponseTimeMessage(publicIPAddress, new Date(), target,
					method, responseTime, responseCode));
			jmsService.sendObjectMessage(metricAggregationMessage);
		}
	}
}