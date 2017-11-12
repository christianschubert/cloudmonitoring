package at.tuwien.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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

	public static String calculateHMac(String key, String message) {
		String algorithm = "HmacSHA256";

		try {
			SecretKey secretKey = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);
			Mac mac = Mac.getInstance(algorithm, new BouncyCastleProvider());
			mac.init(secretKey);
			mac.update(message.getBytes("UTF-8"));

			byte[] encrypted = mac.doFinal();

			return Base64.getEncoder().encodeToString(encrypted);

		} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException
				| UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "";
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

			if (isFile) {
				settings.etcFolderPath = new File(configPath).getParent();
			}

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

			String executionTimeDelayRate = prop.getProperty("execution.time.delay.rate");
			if (executionTimeDelayRate != null) {
				settings.executionTimeDelayRate = Double.parseDouble(executionTimeDelayRate);
			}

			String executionTimeDelayTime = prop.getProperty("execution.time.delay.time");
			if (executionTimeDelayTime != null) {
				settings.executionTimeDelayTime = Integer.parseInt(executionTimeDelayTime);
			}

			String executionTimeDelayVariation = prop.getProperty("execution.time.delay.variation");
			if (executionTimeDelayVariation != null) {
				settings.executionTimeDelayVariation = Integer.parseInt(executionTimeDelayVariation);
			}

			String requestFailRate = prop.getProperty("request.fail.rate");
			if (requestFailRate != null) {
				settings.requestFailRate = Double.parseDouble(requestFailRate);
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

			String uberAgentMonitorInterval = prop.getProperty("uber.agent.monitor.interval");
			if (uberAgentMonitorInterval != null) {
				settings.uberAgentMonitorInterval = Integer.parseInt(uberAgentMonitorInterval);
			}

			boolean logUsageTop = (prop.getProperty("log.usage.top") == null ? false
					: Boolean.valueOf(prop.getProperty("log.usage.top").trim()));
			settings.logUsageTop = logUsageTop;

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