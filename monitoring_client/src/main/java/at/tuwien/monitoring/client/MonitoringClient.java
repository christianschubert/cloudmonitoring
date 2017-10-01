package at.tuwien.monitoring.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.client.constants.Constants;
import at.tuwien.monitoring.client.request.Rotation;
import at.tuwien.monitoring.client.request.ServiceRequester;

public class MonitoringClient {

	private final static Logger logger = Logger.getLogger(MonitoringClient.class);

	private Settings settings;

	private ServiceRequester requester;

	private PrintWriter outLogFile;

	public MonitoringClient(Settings settings) {
		this.settings = settings;
	}

	public void init() {
		if (settings.logMetrics) {
			try {
				FileWriter fw = new FileWriter(settings.etcFolderPath + "/logs/logs_client.csv");
				BufferedWriter bw = new BufferedWriter(fw);
				outLogFile = new PrintWriter(bw);

				// csv header
				outLogFile.println("responseTime");
			} catch (IOException e) {
				settings.logMetrics = false;
				logger.error("Error logging metrics to file.");
			}
		}

		requester = new ServiceRequester(settings.serviceUrl + Constants.APP_PATH);
	}

	public void runTest() {
		boolean batchMode = settings.imageType.equalsIgnoreCase("batch");

		// batch mode starts with small image
		String currentImageType = batchMode ? "small" : settings.imageType;

		logger.info("Test running. Please wait...");

		for (int i = 0; i < settings.requestCount; i++) {
			if (batchMode && i != 0 && (i % (settings.requestCount / 4) == 0)) {
				// switch to next image
				currentImageType = getNextImageForBatch(currentImageType);
			}

			String image = "image_" + currentImageType + ".jpg";

			// start measuring response time from client - to compare with aspect
			long startTime = System.nanoTime();
			requester.shrinkRequest(image, settings.imageTargetSize, Rotation.valueOf(settings.imageRotation));

			if (settings.logMetrics) {
				long responseTime = System.nanoTime() - startTime;
				outLogFile.println(TimeUnit.NANOSECONDS.toMillis(responseTime));
				outLogFile.flush();
			}
		}

		logger.info("Test finished!");
	}

	private String getNextImageForBatch(String currentImageType) {
		switch (currentImageType) {
		case "small":
			return "medium";
		case "medium":
			return "big";
		case "big":
			return "very_big";
		default:
			return currentImageType;
		}
	}

	public void shutdown() {
		logger.info("Monitoring client shutdown.");
		requester.shutdown();

		if (outLogFile != null) {
			outLogFile.close();
		}
	}

	public static void main(final String[] args) {
		Settings settings = Utils.parseArgsForSettings(args);
		MonitoringClient monitoringClient = new MonitoringClient(settings);
		monitoringClient.init();
		monitoringClient.runTest();
		monitoringClient.shutdown();
		System.exit(0);
	}
}
