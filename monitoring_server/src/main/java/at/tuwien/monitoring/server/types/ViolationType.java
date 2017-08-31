package at.tuwien.monitoring.server.types;

public enum ViolationType {
	CPU_LOAD("cpuload"), MEM_TOTAL("totalmemory"), MEM_RESIDENT("residentmemory"), RESPONSE_TIME(
			"responsetime"), SUCCESSABILTY("responsecode"), THROUGHPUT("count(*)");

	private String propName;

	private ViolationType(String propName) {
		this.propName = propName;
	}

	public String getPropName() {
		return propName;
	}

	public static ViolationType get(String propName) {
		for (ViolationType v : values()) {
			if (v.propName.equals(propName.toLowerCase())) {
				return v;
			}
		}
		return null;
	}
}
