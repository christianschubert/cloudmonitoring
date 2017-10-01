package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;

public class CpuMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private long cpuTotal;
	private long cpuUser;
	private long cpuKernel;
	private double cpuUsagePerc;

	public CpuMessage() {
	}

	public CpuMessage(String ipAddress, Date timestamp, String application) {
		super(timestamp, ipAddress);
		setApplication(application);
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public double getCpuUsagePerc() {
		return cpuUsagePerc;
	}

	public void setCpuUsagePerc(double cpuUsagePerc) {
		this.cpuUsagePerc = cpuUsagePerc;
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
				+ ", getCpuUsagePerc=" + getCpuUsagePerc() + ", getCpuTotal=" + getCpuTotal() + ", getCpuUser=" + getCpuUser() + ", getCpuKernel=" + getCpuKernel()
				+ "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("application")
				.add("cpuUsagePerc")
				.add("cpuTotal")
				.add("cpuUser")
				.add("cpuKernel")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(String.valueOf(getTimestamp().getTime()))
				.add(getApplication())
				.add(String.valueOf(getCpuUsagePerc()))
				.add(String.valueOf(getCpuTotal()))
				.add(String.valueOf(getCpuUser()))
				.add(String.valueOf(getCpuKernel()))
				.toString();
	}
}