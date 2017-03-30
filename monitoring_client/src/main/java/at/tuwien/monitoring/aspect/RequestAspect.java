package at.tuwien.monitoring.aspect;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RequestAspect {

	@Pointcut("call (* java.net.HttpURLConnection.getResponseCode(..))")
	public void callGetResonseCode() {
	}

	@Pointcut("!within(RequestAspect)")
	public void notThisAspect() {
	}

	@Around("callGetResonseCode() && notThisAspect()")
	public Object call(ProceedingJoinPoint proceedingJoinPoint) throws IOException {
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
			System.out.println("Method: " + httpURLConnection.getRequestMethod());
			System.out.println("Response Code: " + httpURLConnection.getResponseCode());
		}

		return response;
	}
}