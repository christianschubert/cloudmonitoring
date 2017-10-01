package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;

import at.tuwien.common.Method;

public class ServerInfoMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String target;
	private Method method;
	private long executionTime; // milliseconds

	public ServerInfoMessage() {
	}

	public ServerInfoMessage(String ipAddress, Date timestamp, String target, Method method, long executionTime) {
		super(timestamp, ipAddress);
		setTarget(target);
		setMethod(method);
		setExecutionTime(executionTime);
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

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String getServiceName() {
		return getMethod().toString() + " " + getTarget();
	}

	@Override
	public String toString() {
		return "ServerExecutionTimeMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress() + ", getTarget()=" + getTarget()
				+ ", getMethod()=" + getMethod() + ", getExecutionTime()=" + getExecutionTime() + "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("target")
				.add("method")
				.add("executionTime")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(String.valueOf(getTimestamp().getTime()))
				.add(getTarget())
				.add(getMethod().toString())
				.add(String.valueOf(getExecutionTime()))
				.toString();
	}
}