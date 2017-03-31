package at.tuwien.monitoring.client.aspect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import at.tuwien.monitoring.client.aspect.jms.JmsService;
import at.tuwien.monitoring.client.constants.Constants;

@Aspect
public class RequestAspect {

	private final static Logger logger = Logger.getLogger(RequestAspect.class);

	private JmsService jmsService;

	public RequestAspect() {
		String publicIPAddress = lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);
		jmsService = new JmsService(Constants.brokerURL, publicIPAddress);
		jmsService.start();
	}

	@Override
	protected void finalize() throws Throwable {
		jmsService.stop();
		super.finalize();
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
		long time = System.currentTimeMillis();
		Object response = null;
		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return response;
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time));

		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
		Annotation[] annotations = signature.getMethod().getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation instanceof MonitorRequest) {
				MonitorRequest monitorRequest = (MonitorRequest) annotation;
				System.out.println(monitorRequest.target());
				System.out.println(monitorRequest.method());
				break;
			}
		}

		return response;
	}

	@Around("callGetResonseCode() && notThisAspect()")
	public Object callRequest(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
		long time = System.currentTimeMillis();
		Object response = null;
		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			return response;
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time));

		Object target = proceedingJoinPoint.getTarget();
		if (target instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) target;
			System.out.println("Target: " + httpURLConnection.getURL().toString());
			System.out.println("Method: " + Method.valueOf(httpURLConnection.getRequestMethod()));
			System.out.println("Response Code: " + httpURLConnection.getResponseCode());
		}

		return response;
	}

	private String lookupPublicIPAddress() {
		BufferedReader reader = null;
		try {
			URL checkIP = new URL("http://checkip.amazonaws.com");
			reader = new BufferedReader(new InputStreamReader(checkIP.openStream()));
			return reader.readLine();
		} catch (IOException e) {
			logger.error("Error looking up public IP address.");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}