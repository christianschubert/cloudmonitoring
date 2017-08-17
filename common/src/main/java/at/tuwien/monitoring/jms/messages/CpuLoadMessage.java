package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;

public class CpuLoadMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private double cpuLoad;
	private long cpuTotal;
	private long cpuUser;
	private long cpuKernel;

	public CpuLoadMessage() {
	}

	public CpuLoadMessage(String ipAddress, Date timestamp, String application) {
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
		return "CpuLoadMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress() + ", getApplication=" + getApplication()
				+ ", getCpuLoad=" + getCpuLoad() + ", getCpuTotal=" + getCpuTotal() + ", getCpuUser=" + getCpuUser() + ", getCpuKernel=" + getCpuKernel()
				+ "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("application")
				.add("cpuLoad")
				.add("cpuTotal")
				.add("cpuUser")
				.add("cpuKernel")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(getTimestamp().toString())
				.add(getApplication())
				.add(String.valueOf(getCpuLoad()))
				.add(String.valueOf(getCpuTotal()))
				.add(String.valueOf(getCpuUser()))
				.add(String.valueOf(getCpuKernel()))
				.toString();
	}
}