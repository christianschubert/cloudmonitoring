package at.tuwien.monitoring.agent.constants;

public final class Constants {

	private Constants() {
	}

	public static final String ASPECTJ_WEAVER_PATH = "aspectjweaver/aspectjweaver.jar";

	public static final long JMS_SEND_METRIC_MESSAGES_INTERVAL = 5000; // [ms]

	public static final long PROCESS_CHILDREN_UPDATE_INTERVAL = 2000; // [ms]

	public static final long PROCESS_MONITOR_START_DELAY = 100; // [ms]
	public static final long PROCESS_MONITOR_INTERVAL = 1000; // [ms]
}