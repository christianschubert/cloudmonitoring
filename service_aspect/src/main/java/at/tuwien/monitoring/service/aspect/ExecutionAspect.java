package at.tuwien.monitoring.service.aspect;

import java.lang.annotation.Annotation;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import at.tuwien.common.Method;
import at.tuwien.common.Utils;

@Aspect
public class ExecutionAspect {

	private final static Logger logger = Logger.getLogger(ExecutionAspect.class);

	private String publicIPAddress;

	private ExecutionAspect() {
		publicIPAddress = Utils.lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);
	}

	@Pointcut("@annotation(MonitorRequest)")
	public void hasMonitorRequestAnnotation() {
	}

	@Pointcut("@annotation(javax.ws.rs.POST)")
	public void hasPostAnnotation() {
	}

	@Pointcut("@annotation(javax.ws.rs.GET)")
	public void hasGetAnnotation() {
	}

	@Pointcut("@annotation(javax.ws.rs.PUT)")
	public void hasPutAnnotation() {
	}

	@Pointcut("@annotation(javax.ws.rs.DELETE)")
	public void hasDeleteAnnotation() {
	}

	@Pointcut("@annotation(javax.ws.rs.HEAD)")
	public void hasHeadAnnotation() {
	}

	@Pointcut("execution(* *(..))")
	public void atExecution() {
	}

	@Around("atExecution() && (hasMonitorRequestAnnotation() || "
			+ "hasPostAnnotation() || hasGetAnnotation() || hasPutAnnotation() || hasDeleteAnnotation() || hasHeadAnnotation())")
	public Object annotationRequest(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		Throwable ex = null;
		Object response = null;

		long startTime = System.currentTimeMillis();

		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			ex = throwable;
		}

		long responseTime = System.currentTimeMillis() - startTime;

		Method method = null;
		String target = "";

		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

		// check annotations of the surrounding class for path annotation
		Annotation[] annotationsClass = signature.getDeclaringType().getAnnotations();
		for (Annotation annotation : annotationsClass) {
			if (annotation instanceof Path) {
				Path path = (Path) annotation;
				target += path.value();
				break;
			}
		}

		// check annotations of function - find out method (POST, GET, etc.) and
		// path/target of operation
		Annotation[] annotationsMethod = signature.getMethod().getAnnotations();
		for (Annotation annotation : annotationsMethod) {
			if (annotation instanceof MonitorRequest) {
				MonitorRequest monitorRequest = (MonitorRequest) annotation;
				method = monitorRequest.method();
				target = monitorRequest.target();
			} else if (annotation instanceof Path) {
				Path path = (Path) annotation;

				// append to path of class
				if (target != null && !target.isEmpty()) {
					target += "/";
				}
				target += path.value();
			} else if (method == null) {
				method = resolveMethodAnnotation(annotation);
			}
		}

		System.out.println(method + " " + target);

		if (method != null && target != null && !target.isEmpty()) {
			sendExecutionTime(target, method, responseTime);
		} else {
			logger.error("Cannot send execution time. Reason: Error finding out method or target.");
		}

		if (ex != null) {
			throw ex;
		}

		return response;
	}

	private Method resolveMethodAnnotation(Annotation annotation) {
		for (Method method : Method.values()) {
			if (annotation.toString().toLowerCase().contains(method.toString().toLowerCase())) {
				return method;
			}
		}
		return null;
	}

	private void sendExecutionTime(String target, Method method, long responseTime) {

	}
}