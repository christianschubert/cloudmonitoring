package at.tuwien.monitoring.agent.jms.messages;

import java.util.Date;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private double cpuLoad;

	public CpuLoadMessage(String application, Date timestamp, double cpuLoad) {
		super(application, timestamp);
		this.setCpuLoad(cpuLoad);
	}

	public double getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}
}