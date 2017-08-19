package at.tuwien.monitoring.server.processing.mapping;

public class SimplePredicate {

	private String predicateSign;
	private String detectionSign;
	private String slaParameter;
	private double threshold;

	public SimplePredicate() {
	}

	public String getPredicateSign() {
		return predicateSign;
	}

	public void setPredicateSign(String predicateSign) {
		this.predicateSign = predicateSign;
	}

	public String getSlaParameter() {
		return slaParameter;
	}

	public void setSlaParameter(String slaParameter) {
		this.slaParameter = slaParameter;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public String getDetectionSign() {
		return detectionSign;
	}

	public void setDetectionSign(String detectionSign) {
		this.detectionSign = detectionSign;
	}

	@Override
	public String toString() {
		return "SimplePredicate [predicateSign=" + predicateSign + ", detectionSign=" + detectionSign
				+ ", slaParameter=" + slaParameter + ", threshold=" + threshold + "]";
	}
}