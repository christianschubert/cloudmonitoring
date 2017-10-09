package at.tuwien.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Utils {

	private final static Logger logger = Logger.getLogger(Utils.class);

	private Utils() {
	}

	public static String lookupPublicIPAddress() {
		BufferedReader reader = null;
		try {
			URL checkIP = new URL("http://checkip.amazonaws.com");
			reader = new BufferedReader(new InputStreamReader(checkIP.openStream()));
			return reader.readLine();
		} catch (IOException e) {
			logger.error("Error looking up public IP address.");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Settings parseArgsForSettings(String[] args) {
		Settings settings = new Settings();

		for (String arg : args) {
			if (!arg.startsWith("config:")) {
				continue;
			}
			String split[] = arg.split("config:");
			if (split.length != 2) {
				continue;
			}
			settings = Utils.readProperties(split[1]);
		}

		return settings;
	}

	private static Settings readProperties(String configPath) {
		Settings settings = new Settings();

		boolean isFile = configPath.endsWith(".properties");

		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = isFile ? new FileInputStream(configPath) : new URL(configPath).openStream();
			prop.load(in);

			String brokerUrl = prop.getProperty("broker.url");
			if (brokerUrl != null) {
				settings.brokerUrl = brokerUrl.trim();
			}

			String serviceUrl = prop.getProperty("service.url");
			if (serviceUrl != null) {
				settings.serviceUrl = serviceUrl.trim();
			}

			String wslaFile = prop.getProperty("wsla.file");
			if (wslaFile != null) {
				settings.wslaFile = wslaFile.trim();
			}

			boolean embeddedBroker = (prop.getProperty("embedded.broker") == null ? false
					: Boolean.valueOf(prop.getProperty("embedded.broker").trim()));
			settings.embeddedBroker = embeddedBroker;

			boolean logMetrics = (prop.getProperty("log.metrics") == null ? false
					: Boolean.valueOf(prop.getProperty("log.metrics").trim()));
			settings.logMetrics = logMetrics;

			String imageType = prop.getProperty("image.type");
			if (imageType != null) {
				settings.imageType = imageType.trim();
			}

			String imageRotation = prop.getProperty("image.rotation");
			if (imageRotation != null) {
				settings.imageRotation = imageRotation.trim();
			}

			String imageTargetSize = prop.getProperty("image.target.size");
			if (imageTargetSize != null) {
				settings.imageTargetSize = Integer.parseInt(imageTargetSize);
			}

			String requestCount = prop.getProperty("request.count");
			if (requestCount != null) {
				settings.requestCount = Integer.parseInt(requestCount);
			}

			String responseTimeDelayRate = prop.getProperty("response.time.delay.rate");
			if (responseTimeDelayRate != null) {
				settings.responseTimeDelayRate = Double.parseDouble(responseTimeDelayRate);
			}

			String responseTimeDelayTime = prop.getProperty("response.time.delay.time");
			if (responseTimeDelayTime != null) {
				settings.responseTimeDelayTime = Integer.parseInt(responseTimeDelayTime);
			}

			String metricsAggregationInterval = prop.getProperty("metrics.aggregation.interval");
			if (metricsAggregationInterval != null) {
				settings.metricsAggregationInterval = Integer.parseInt(metricsAggregationInterval);
			}

			String processChildrenUpdateInterval = prop.getProperty("process.children.update.interval");
			if (processChildrenUpdateInterval != null) {
				settings.processChildrenUpdateInterval = Integer.parseInt(processChildrenUpdateInterval);
			}

			String systemMetricsMonitorInterval = prop.getProperty("system.metrics.monitor.interval");
			if (systemMetricsMonitorInterval != null) {
				settings.systemMetricsMonitorInterval = Integer.parseInt(systemMetricsMonitorInterval);
			}

			settings.etcFolderPath = new File(configPath).getParent();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return settings;
	}
}