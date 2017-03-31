package at.tuwien.monitoring.jms.messages;

import java.util.Date;

import at.tuwien.common.Method;

public class ClientResponseTimeMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String target;
	private Method method;
	private long responseTime; // milliseconds

	public ClientResponseTimeMessage(long timestamp, String target, Method method, long responseTime) {
		super(timestamp);
		setTarget(target);
		setMethod(method);
		setResponseTime(responseTime);
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	@Override
	public String toString() {
		return "ClientResponseTimeMessage [Timestamp=" + new Date(getTimestamp()) + ", Target=" + getTarget()
				+ ", Method=" + getMethod().toString() + ", ResponseTime=" + getResponseTime() + "]";
	}
}