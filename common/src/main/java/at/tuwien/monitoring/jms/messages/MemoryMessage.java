package at.tuwien.monitoring.jms.messages;

import java.util.Date;
import java.util.StringJoiner;

public class MemoryMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private long totalMemory;
	private long residentMemory;

	public MemoryMessage() {
	}

	public MemoryMessage(String ipAddress, Date timestamp, String application, long totalMemory, long residentMemory) {
		super(timestamp, ipAddress);
		setApplication(application);
		setTotalMemory(totalMemory);
		setResidentMemory(residentMemory);
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getResidentMemory() {
		return residentMemory;
	}

	public void setResidentMemory(long residentMemory) {
		this.residentMemory = residentMemory;
	}

	@Override
	public String getServiceName() {
		return getApplication();
	}

	@Override
	public String toString() {
		return "MemoryMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress() + ", getApplication()=" + getApplication()
				+ ", getTotalMemory()=" + getTotalMemory() + ", getResidentMemory()=" + getResidentMemory() + "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("application")
				.add("totalMemory")
				.add("residentMemory")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(getTimestamp().toString())
				.add(getApplication())
				.add(String.valueOf(getTotalMemory()))
				.add(String.valueOf(getResidentMemory()))
				.toString();
	}
}