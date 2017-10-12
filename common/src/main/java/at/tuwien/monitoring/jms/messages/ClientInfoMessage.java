package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;
import at.tuwien.common.Method;

public class ClientInfoMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private int id;
	private String target;
	private Method method;
	private long responseTime; // milliseconds
	private int responseCode;
	
	public ClientInfoMessage() {
	}

	public ClientInfoMessage(int id, String ipAddress, Date timestamp, String target, Method method, long responseTime,
			final int responseCode) {
		super(timestamp, ipAddress);
		setId(id);
		setTarget(target);
		setMethod(method);
		setResponseTime(responseTime);
		setResponseCode(responseCode);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String getServiceName() {
		return getMethod().toString() + " " + getTarget();
	}

	@Override
	public String toString() {
		return "ClientResponseTimeMessage [getId()=" + getId() + ", getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getTarget()=" + getTarget() + ", getMethod()=" + getMethod() + ", getResponseTime()="
				+ getResponseTime() + ", getResponseCode()=" + getResponseCode() + "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("id")
				.add("timestamp")
				.add("target")
				.add("method")
				.add("responseTime")
				.add("responseCode")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(String.valueOf(getId()))
				.add(String.valueOf(getTimestamp().getTime()))
				.add(getTarget())
				.add(getMethod().toString())
				.add(String.valueOf(getResponseTime()))
				.add(String.valueOf(responseCode))
				.toString();
	}
}