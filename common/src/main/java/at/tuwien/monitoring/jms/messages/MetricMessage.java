package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;
import java.util.Date;

import at.tuwien.common.Loggable;

public abstract class MetricMessage implements Serializable, Loggable {

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String ipAddress;

	public MetricMessage() {
	}

	public MetricMessage(Date timestamp, String ipAddress) {
		this.timestamp = timestamp;
		this.ipAddress = ipAddress;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public abstract String getServiceName();
}