package at.tuwien.monitoring.jms.messages;

import java.util.Date;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private double cpuLoad;

	public CpuLoadMessage(String application, long timestamp, double cpuLoad) {
		super(application, timestamp);
		this.setCpuLoad(cpuLoad);
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