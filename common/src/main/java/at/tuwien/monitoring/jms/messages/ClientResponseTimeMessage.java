package at.tuwien.monitoring.jms.messages;

import at.tuwien.common.Method;

public class ClientResponseTimeMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String target;
	private Method method;
	private long responseTime; // milliseconds
	private int responseCode;

	public ClientResponseTimeMessage(final String ipAddress, final long timestamp, final String target,
			final Method method, final long responseTime, final int responseCode) {
		super(timestamp, ipAddress);
		setTarget(target);
		setMethod(method);
		setResponseTime(responseTime);
		setResponseCode(responseCode);
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

	public long getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(final long responseTime) {
		this.responseTime = responseTime;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(final int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "ClientResponseTimeMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getTarget()=" + getTarget() + ", getMethod()=" + getMethod() + ", getResponseTime()=" + getResponseTime()
				+ ", getResponseCode()=" + getResponseCode() + "]";
	}
}