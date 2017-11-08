package at.tuwien.monitoring.service.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.tuwien.common.GlobalConstants;
import at.tuwien.common.Method;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.jms.messages.ServerInfoMessage;

@Aspect
public class ExecutionAspect {

	private final static Logger logger = Logger.getLogger(ExecutionAspect.class);

	private String publicIPAddress;

	private Socket socket;
	private PrintWriter toServer;

	private ObjectMapper mapper = new ObjectMapper();

	private ExecutionAspect() {
		publicIPAddress = Utils.lookupPublicIPAddress();
		logger.info("Public IP address of client: " + publicIPAddress);

		try {
			socket = new Socket(InetAddress.getLoopbackAddress(), GlobalConstants.EXTENSION_SERVER_PORT);
			toServer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			logger.error("Error connecting to agent. " + e.getMessage());
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (toServer != null) {
				toServer.close();
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}));
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
	public Object annotationRequest(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		if (socket == null) {
			// there is no need to measure execution time if agent is not
			// available
			return proceedingJoinPoint.proceed();
		}

		Throwable ex = null;
		Object response = null;

		long startTime = System.nanoTime();

		try {
			response = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			ex = throwable;
		}

		long executionTime = System.nanoTime() - startTime;
		MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();

		ExtracterSender extracterSender = new ExtracterSender(executionTime, signature);
		new Thread(extracterSender).start();

		if (ex != null) {
			throw ex;
		}

		return response;

	}

	class ExtracterSender implements Runnable {

		private long executionTime;
		private MethodSignature signature;

		public ExtracterSender(long executionTime, MethodSignature signature) {
			this.executionTime = executionTime;
			this.signature = signature;
		}

		@Override
		public void run() {
			Method method = null;
			String target = "";

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

			if (method != null && target != null && !target.isEmpty()) {
				sendExecutionTime(target, method, executionTime);
			} else {
				logger.error("Cannot send execution time. Reason: Error finding out method or target.");
			}
		}

		private Method resolveMethodAnnotation(Annotation annotation) {
			for (Method method : Method.values()) {
				if (annotation.toString().toLowerCase().contains(method.toString().toLowerCase())) {
					return method;
				}
			}
			return null;
		}

		private void sendExecutionTime(String target, Method method, long executionTime) {
			ServerInfoMessage message = new ServerInfoMessage(publicIPAddress, new Date(), target, method,
					TimeUnit.NANOSECONDS.toMillis(executionTime));
			synchronized (toServer) {
				try {
					toServer.println(mapper.writeValueAsString(message));
				} catch (JsonProcessingException e) {
					logger.error("Error mapping object to json. " + e.getMessage());
				}
			}
		}
	}
}