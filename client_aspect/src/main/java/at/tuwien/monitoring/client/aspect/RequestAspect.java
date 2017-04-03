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
import at.tuwien.monitoring.client.constants.Constants;
import at.tuwien.monitoring.jms.JmsSenderService;
import at.tuwien.monitoring.jms.messages.ClientResponseTimeMessage;

@Aspect
public class RequestAspect {

	private final static Logger logger = Logger.getLogger(RequestAspect.class);

	private JmsSenderService jmsService;

	public RequestAspect() {
		String publicIPAddress = Utils.lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);
		jmsService = new JmsSenderService(Constants.brokerURL, publicIPAddress, GlobalConstants.QUEUE_CLIENTS);
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
	public Object annotationRequest(ProceedingJoinPoint proceedingJoinPoint) throws IOException {

		long startTime = System.currentTimeMillis();

		Object response = null;
		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return response;
		}

		long responseTime = System.currentTimeMillis() - startTime;

		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Annotation[] annotations = signature.getMethod().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof MonitorRequest) {
				MonitorRequest monitorRequest = (MonitorRequest) annotation;
				sendReponseTime(monitorRequest.target(), monitorRequest.method(), responseTime);
				break;
			}
		}

		return response;
	}

	@Around("callGetResonseCode() && notThisAspect()")
	public Object callRequest(ProceedingJoinPoint proceedingJoinPoint) throws IOException {

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
			sendReponseTime(httpURLConnection.getURL().toString(), Method.valueOf(httpURLConnection.getRequestMethod()),
					responseTime);
			// System.out.println("Response Code: " +
			// httpURLConnection.getResponseCode());
		}

		return response;
	}

	public void sendReponseTime(String target, Method method, long responseTime) {
		if (jmsService.isConnected()) {
			jmsService.sendObjectMessage(
					new ClientResponseTimeMessage(new Date().getTime(), target, method, responseTime));
		}
	}
}