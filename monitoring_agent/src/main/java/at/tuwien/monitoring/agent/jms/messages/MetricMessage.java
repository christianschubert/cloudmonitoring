package at.tuwien.monitoring.agent.jms.messages;

import java.io.Serializable;
import java.util.Date;

public abstract class MetricMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String application;

	public MetricMessage(String application, Date timestamp) {
		this.timestamp = timestamp;
		this.application = application;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}