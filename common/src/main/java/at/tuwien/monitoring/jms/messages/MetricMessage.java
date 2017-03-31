package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;

public abstract class MetricMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private long timestamp;

	public MetricMessage(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}