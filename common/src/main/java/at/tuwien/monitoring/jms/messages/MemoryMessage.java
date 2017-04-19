package at.tuwien.monitoring.jms.messages;

import java.util.Date;

public class MemoryMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private String application;
	private long totalMemory;
	private long residentMemory;

	public MemoryMessage(final String ipAddress, final Date timestamp, String application, long totalMemory,
			long residentMemory) {
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
		return "MemoryMessage [getTimestamp()=" + getTimestamp() + ", getIpAddress()=" + getIpAddress()
				+ ", getApplication()=" + getApplication() + ", getTotalMemory()=" + getTotalMemory()
				+ ", getResidentMemory()=" + getResidentMemory() + "]";
	}
}