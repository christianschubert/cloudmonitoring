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

	public CpuMessage(String ipAddress, Date timestamp, String application, double cpuUsagePerc, long cpuTotal, long cpuKernel, long cpuUser) {
		super(timestamp, ipAddress);
		setApplication(application);
		setCpuUsagePerc(cpuUsagePerc);
		setCpuTotal(cpuTotal);
		setCpuKernel(cpuKernel);
		setCpuUser(cpuUser);
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
		return "CpuLoadMessage [getTimestamp()=" + String.valueOf(getTimestamp().getTime()) + ", getIpAddress()=" + getIpAddress() + ", getApplication=" + getApplication()
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + (int) (cpuKernel ^ (cpuKernel >>> 32));
		result = prime * result + (int) (cpuTotal ^ (cpuTotal >>> 32));
		long temp;
		temp = Double.doubleToLongBits(cpuUsagePerc);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (cpuUser ^ (cpuUser >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CpuMessage other = (CpuMessage) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (cpuKernel != other.cpuKernel)
			return false;
		if (cpuTotal != other.cpuTotal)
			return false;
		if (Double.doubleToLongBits(cpuUsagePerc) != Double.doubleToLongBits(other.cpuUsagePerc))
			return false;
		if (cpuUser != other.cpuUser)
			return false;
		return true;
	}
}