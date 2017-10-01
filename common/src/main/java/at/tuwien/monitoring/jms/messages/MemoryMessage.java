package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;

public class MemoryMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private long virtualMemory;
	private long residentMemory;
	private long sharedMemory;
	private double memoryUsagePerc;

	public MemoryMessage(String ipAddress, Date timestamp, String application, long virtualMemory, long residentMemory,
			long sharedMemory, double memoryUsagePerc) {
		super(timestamp, ipAddress);
		setApplication(application);
		setVirtualMemory(virtualMemory);
		setResidentMemory(residentMemory);
		setSharedMemory(sharedMemory);
		setMemoryUsagePerc(memoryUsagePerc);
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public long getVirtualMemory() {
		return virtualMemory;
	}

	public void setVirtualMemory(long virtualMemory) {
		this.virtualMemory = virtualMemory;
	}

	public long getResidentMemory() {
		return residentMemory;
	}

	public void setResidentMemory(long residentMemory) {
		this.residentMemory = residentMemory;
	}

	public long getSharedMemory() {
		return sharedMemory;
	}

	public void setSharedMemory(long sharedMemory) {
		this.sharedMemory = sharedMemory;
	}

	public double getMemoryUsagePerc() {
		return memoryUsagePerc;
	}

	public void setMemoryUsagePerc(double memoryUsagePerc) {
		this.memoryUsagePerc = memoryUsagePerc;
	}

	@Override
	public String getServiceName() {
		return getApplication();
	}

	@Override
	public String toString() {
		return "MemoryMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getApplication()=" + getApplication() + ", getVirtualMemory()=" + getVirtualMemory()
				+ ", getResidentMemory()=" + getResidentMemory() + ", getSharedMemory()=" + getSharedMemory()
				+ ", getMemoryUsagePerc()=" + getMemoryUsagePerc() + "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("application")
				.add("memoryUsagePerc")
				.add("virtualMemory")
				.add("residentMemory")
				.add("sharedMemory")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(String.valueOf(getTimestamp().getTime()))
				.add(getApplication())
				.add(String.valueOf(getMemoryUsagePerc()))
				.add(String.valueOf(getVirtualMemory()))
				.add(String.valueOf(getResidentMemory()))
				.add(String.valueOf(getSharedMemory()))
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((application == null) ? 0 : application.hashCode());
		result = prime * result + (int) (residentMemory ^ (residentMemory >>> 32));
		result = prime * result + (int) (sharedMemory ^ (sharedMemory >>> 32));
		result = prime * result + (int) (virtualMemory ^ (virtualMemory >>> 32));
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
		MemoryMessage other = (MemoryMessage) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (residentMemory != other.residentMemory)
			return false;
		if (sharedMemory != other.sharedMemory)
			return false;
		if (virtualMemory != other.virtualMemory)
			return false;
		return true;
	}
}