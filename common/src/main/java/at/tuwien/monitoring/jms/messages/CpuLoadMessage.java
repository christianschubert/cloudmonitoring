package at.tuwien.monitoring.jms.messages;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private double cpuLoad;

	public CpuLoadMessage(final String ipAddress, final long timestamp, String application, double cpuLoad) {
		super(timestamp, ipAddress);
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
		return "CpuLoadMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getApplication()=" + getApplication() + ", getCpuLoad()=" + getCpuLoad() + "]";
	}
}