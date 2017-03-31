package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;

public abstract class MetricMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private long timestamp;
	private String application;

	public MetricMessage(String application, long timestamp) {
		this.timestamp = timestamp;
		this.application = application;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}