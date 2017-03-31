package at.tuwien.monitoring.jms.messages;

import java.util.Date;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private double cpuLoad;

	public CpuLoadMessage(String application, long timestamp, double cpuLoad) {
		super(timestamp);
		setApplication(application);
		setCpuLoad(cpuLoad);
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public double getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}

	@Override
	public String toString() {
		return "CpuLoadMessage [Application=" + getApplication() + ", Timestamp=" + new Date(getTimestamp())
				+ ", CPULoad=" + getCpuLoad() + "]";
	}
}