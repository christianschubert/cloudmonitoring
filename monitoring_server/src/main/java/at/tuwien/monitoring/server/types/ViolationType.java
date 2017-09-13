package at.tuwien.monitoring.server.types;

public enum ViolationType {
	CPU_LOAD("cpuload"), MEM_TOTAL("totalmemory"), MEM_RESIDENT("residentmemory"), RESPONSE_TIME(
			"responsetime"), SUCCESSABILTY("responsecode"), THROUGHPUT("count(*)");

	private final String propName;

	ViolationType(String propName) {
		this.propName = propName;
	}

	public String getPropName() {
		return propName;
	}

	public static ViolationType get(String propName) {
		for (ViolationType v : values()) {
			if (propName.toLowerCase().startsWith(v.propName)) {
				return v;
			}
		}
		return null;
	}
}
