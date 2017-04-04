package at.tuwien.monitoring.server.db.dto;

import at.tuwien.monitoring.server.types.ViolationType;

public class ViolationDTO {

	private String serviceIP;
	private String serviceName;
	private ViolationType violationType;
	private double monitoredValue;
	private double requiredValue;

	public ViolationDTO(String serviceIP, String serviceName, ViolationType violationType, double monitoredValue,
			double requiredValue) {
		super();
		this.serviceIP = serviceIP;
		this.serviceName = serviceName;
		this.violationType = violationType;
		this.monitoredValue = monitoredValue;
		this.requiredValue = requiredValue;
	}

	public String getServiceIP() {
		return serviceIP;
	}

	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
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

	public double getRequiredValue() {
		return requiredValue;
	}

	public void setRequiredValue(double requiredValue) {
		this.requiredValue = requiredValue;
	}

	@Override
	public String toString() {
		return "ViolationDTO [serviceIP=" + serviceIP + ", serviceName=" + serviceName + ", violationType="
				+ violationType + ", monitoredValue=" + monitoredValue + ", requiredValue=" + requiredValue + "]";
	}
}