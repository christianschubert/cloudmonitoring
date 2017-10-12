package at.tuwien.monitoring.server.db.dto;

import java.util.Date;
import java.util.StringJoiner;

import at.tuwien.common.Loggable;

public class ViolationDTO implements Loggable {

	private int id;
	private String sourceIpAddress;
	private String serviceName;
	private String violationType;
	private double monitoredValue;
	private String requiredDesc;
	private Date violationTimestamp;
	private int sourceMessageId;

	public ViolationDTO() {
	}

	public ViolationDTO(String sourceIpAddress, String serviceName, String violationType, double monitoredValue,
			String requiredDesc, Date violationTimestamp) {
		super();
		this.sourceIpAddress = sourceIpAddress;
		this.serviceName = serviceName;
		this.violationType = violationType;
		this.monitoredValue = monitoredValue;
		this.requiredDesc = requiredDesc;
		this.violationTimestamp = violationTimestamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getSourceMessageId() {
		return sourceMessageId;
	}

	public void setSourceMessageId(int sourceMessageId) {
		this.sourceMessageId = sourceMessageId;
	}

	public String getSourceIpAddress() {
		return sourceIpAddress;
	}

	public void setSourceIpAddress(String sourceIpAddress) {
		this.sourceIpAddress = sourceIpAddress;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getViolationType() {
		return violationType;
	}

	public void setViolationType(String violationType) {
		this.violationType = violationType;
	}

	public double getMonitoredValue() {
		return monitoredValue;
	}

	public void setMonitoredValue(double monitoredValue) {
		this.monitoredValue = monitoredValue;
	}

	public String getRequiredDesc() {
		return requiredDesc;
	}

	public void setRequiredDesc(String requiredDesc) {
		this.requiredDesc = requiredDesc;
	}

	public Date getViolationTimestamp() {
		return violationTimestamp;
	}

	public void setViolationTimestamp(Date violationTimestamp) {
		this.violationTimestamp = violationTimestamp;
	}

	@Override
	public String toString() {
		return "ViolationDTO [id=" + id + ", sourceIpAddress=" + sourceIpAddress + ", serviceName=" + serviceName
				+ ", violationType=" + violationType + ", monitoredValue=" + monitoredValue + ", requiredDesc="
				+ requiredDesc + ", violationTimestamp=" + violationTimestamp + "]";
	}

	@Override
	public String getCsvHeader() {
		return new StringJoiner(";")
				.add("timestamp")
				.add("serviceName")
				.add("sourceIp")
				.add("violationType")
				.add("monitoredValue")
				.add("requiredDesc")
				.add("sourceMessageId")
				.toString();
	}

	@Override
	public String toCsvEntry() {
		return new StringJoiner(";")
				.add(String.valueOf(getViolationTimestamp().getTime()))
				.add(getServiceName())
				.add(getSourceIpAddress())
				.add(getViolationType())
				.add(String.valueOf(getMonitoredValue()))
				.add(getRequiredDesc())
				.add(sourceMessageId == 0 ? "" : String.valueOf(getSourceMessageId()))
				.toString();
	}
}