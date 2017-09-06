package at.tuwien.common;

public class Settings {
	public String etcFolderPath = System.getProperty("user.home") + "/cloudmonitoring_logs";

	public String brokerUrl = "tcp://localhost:61616";
	public String serviceUrl = "http://localhost:8080";

	public boolean logMetrics = true;

	public String imageRotation = "NONE";
	public String imageType = "medium";
	public int imageTargetSize = 300;
	public int requestCount = 100;

	public int metricsAggregationInterval = 5000;
	public int processChildrenUpdateInterval = 2000;
	public int systemMetricsMonitorInterval = 1000;
}
