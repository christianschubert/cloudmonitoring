package at.tuwien.monitoring.client;

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

	public MonitoringClient(Settings settings) {
		this.settings = settings;
	}

	public void init() {
		requester = new ServiceRequester(settings.serviceUrl + Constants.APP_PATH, settings);
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
			requester.shrinkRequest(image, settings.imageTargetSize, Rotation.valueOf(settings.imageRotation));

			System.out.println("Request " + i + " finished");
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
