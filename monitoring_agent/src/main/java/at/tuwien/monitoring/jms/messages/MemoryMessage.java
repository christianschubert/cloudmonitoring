package at.tuwien.monitoring.jms.messages;

import java.util.Date;

public class MemoryMessage extends MetricMessage {

	private static final long serialVersionUID = 1L;

	private long totalMemory;
	private long residentMemory;

	public MemoryMessage(String application, long timestamp, long totalMemory, long residentMemory) {
		super(application, timestamp);
		this.totalMemory = totalMemory;
		this.residentMemory = residentMemory;
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
	public String toString() {
		return "MemoryMessage [Application=" + getApplication() + ", Timestamp=" + new Date(getTimestamp())
				+ ", Total memory=" + totalMemory + ", Resident memory=" + residentMemory + "]";
	}
}