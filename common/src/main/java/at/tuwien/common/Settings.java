package at.tuwien.common;

/**
 * The {@code Settings} class provides a configuration to the monitoring
 * framework.
 * 
 * <p>
 * A new instance of this class has default values set for each field.
 */
public class Settings {
	public String etcFolderPath = System.getProperty("user.home") + "/cloudmonitoring/etc";

	public boolean embeddedBroker = true;

	public String brokerUrl = "tcp://localhost:61616";
	public String serviceUrl = "http://localhost:8080";

	public String wslaFile = "image_service_agreement.xml";

	public boolean logMetrics = true;

	public String imageRotation = "NONE";
	public String imageType = "medium";
	public int imageTargetSize = 300;
	public int requestCount = 100;

	public double executionTimeDelayRate = 0.0d;
	public int executionTimeDelayTime = 2000;
	public int executionTimeDelayVariation = 100;
	public double requestFailRate = 0.0d;

	public int metricsAggregationInterval = 5000;
	public int processChildrenUpdateInterval = 2000;
	public int systemMetricsMonitorInterval = 1000;

	public boolean logUsageTop = false;
}