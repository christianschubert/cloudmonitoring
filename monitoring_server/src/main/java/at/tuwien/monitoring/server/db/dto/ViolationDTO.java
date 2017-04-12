package at.tuwien.monitoring.server.db.dto;

import java.util.Date;

import at.tuwien.monitoring.server.types.ViolationType;

public class ViolationDTO {

	private int id;
	private String sourceIpAddress;
	private String serviceName;
	private ViolationType violationType;
	private double monitoredValue;
	private String requiredDesc;
	private Date violationTimestamp;

	public ViolationDTO() {
	}

	public ViolationDTO(String sourceIpAddress, String serviceName, ViolationType violationType, double monitoredValue,
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

	public ViolationType getViolationType() {
		return violationType;
	}

	public void setViolationType(ViolationType violationType) {
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
				+ ", violationType=" + violationType + ", monitoredValue=" + monitoredValue + ", requiredDesc=" + requiredDesc
				+ ", violationTimestamp=" + violationTimestamp + "]";
	}
}