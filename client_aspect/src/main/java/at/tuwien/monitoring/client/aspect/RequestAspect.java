package at.tuwien.monitoring.client.aspect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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
import at.tuwien.monitoring.jms.messages.ClientInfoMessage;
import at.tuwien.monitoring.jms.messages.MetricAggregationMessage;

@Aspect
public class RequestAspect {

	private final static Logger logger = Logger.getLogger(RequestAspect.class);

	private static Settings settings;

	private JmsSenderService jmsService;
	private String publicIPAddress;

	private PrintWriter outLogFile;
	private boolean writeHeader = true;

	private RequestAspect() {
		publicIPAddress = Utils.lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (jmsService != null) {
				jmsService.stop();
			}

			if (outLogFile != null) {
				outLogFile.close();
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

		settings = Utils.parseArgsForSettings(stringArgs);

		jmsService = new JmsSenderService(settings.brokerUrl, GlobalConstants.QUEUE_CLIENTS);
		jmsService.start();

		if (settings.logMetrics) {
			try {
				FileWriter fw = new FileWriter(settings.etcFolderPath + "/logs/logs_client_aspect.csv");
				BufferedWriter bw = new BufferedWriter(fw);
				outLogFile = new PrintWriter(bw);
			} catch (IOException e) {
				settings.logMetrics = false;
				logger.error("Error logging metrics to file.");
			}
		}
	}

	@Pointcut("execution(* at.tuwien.monitoring.server.MonitoringServer.init(..))")
	public void serverInit() {
	}

	// for integration test; client can only connect to server after it has started
	@After("serverInit()")
	public void serverInitAdvice() {
		if (!jmsService.isConnected()) {
			jmsService.start();
		}
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
			sendMessage(clientRequest.getUri().toURL().toString(), Method.valueOf(clientRequest.getMethod()),
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
	public Object annotationRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

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
				sendMessage(monitorRequest.target(), monitorRequest.method(), responseTime, responseCode);
				break;
			}
		}

		if (ex != null) {
			throw ex;
		}

		return response;
	}

	public void sendMessage(String target, Method method, long responseTime, int responseCode) {
		if (jmsService.isConnected() || settings.logMetrics) {

			// convert nanoseconds to milliseconds, responsetime is -1 for requests with
			// errors
			long responseTimeMillis = (responseTime == -1 ? -1 : TimeUnit.NANOSECONDS.toMillis(responseTime));

			ClientInfoMessage clientResponseTimeMessage = new ClientInfoMessage(publicIPAddress, new Date(), target,
					method, responseTimeMillis, responseCode);

			if (settings.logMetrics) {
				if (writeHeader) {
					outLogFile.println(clientResponseTimeMessage.getCsvHeader());
					writeHeader = false;
				}
				outLogFile.println(clientResponseTimeMessage.toCsvEntry());
				outLogFile.flush();
			}

			if (jmsService.isConnected()) {
				jmsService.sendObjectMessage(new MetricAggregationMessage(clientResponseTimeMessage));
			}
		}
	}
}