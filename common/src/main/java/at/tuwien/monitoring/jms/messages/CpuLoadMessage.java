package at.tuwien.monitoring.jms.messages;

import java.util.Date;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private double cpuLoad;
	private long cpuTotal;
	private long cpuUser;
	private long cpuKernel;

	public CpuLoadMessage() {
	}

	public CpuLoadMessage(final String ipAddress, final Date timestamp, String application) {
		super(timestamp, ipAddress);
		setApplication(application);
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

	public long getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(long cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	public long getCpuUser() {
		return cpuUser;
	}

	public void setCpuUser(long cpuUser) {
		this.cpuUser = cpuUser;
	}

	public long getCpuKernel() {
		return cpuKernel;
	}

	public void setCpuKernel(long cpuKernel) {
		this.cpuKernel = cpuKernel;
	}

	@Override
	public String getServiceName() {
		return getApplication();
	}

	@Override
	public String toString() {
		return "CpuLoadMessage [application=" + application + ", cpuLoad=" + getCpuLoad() + ", cpuTotal="
				+ getCpuTotal() + ", cpuUser=" + getCpuUser() + ", cpuKernel=" + getCpuKernel() + "]";
	}
}