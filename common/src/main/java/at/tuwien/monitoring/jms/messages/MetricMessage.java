package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;

public abstract class MetricMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private long timestamp;
	private String ipAddress;

	public MetricMessage(long timestamp, String ipAddress) {
		this.timestamp = timestamp;
		this.ipAddress = ipAddress;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}