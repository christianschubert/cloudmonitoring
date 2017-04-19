package at.tuwien.monitoring.jms.messages;

import java.util.Date;

import at.tuwien.common.Method;

public class ServerExecutionTimeMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String target;
	private Method method;
	private long executionTime; // milliseconds

	public ServerExecutionTimeMessage(final String ipAddress, final Date timestamp, final String target,
			final Method method, final long executionTime) {
		super(timestamp, ipAddress);
		setTarget(target);
		setMethod(method);
		setExecutionTime(executionTime);
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(final Method method) {
		this.method = method;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(final long executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String getServiceName() {
		return getMethod().toString() + " " + getTarget();
	}

	@Override
	public String toString() {
		return "ClientResponseTimeMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getTarget()=" + getTarget() + ", getMethod()=" + getMethod() + ", getResponseTime()="
				+ getExecutionTime() + "]";
	}
}