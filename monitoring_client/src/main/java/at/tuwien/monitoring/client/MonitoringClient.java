package at.tuwien.monitoring.client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.client.constants.Constants;
import at.tuwien.monitoring.client.request.Rotation;
import at.tuwien.monitoring.client.request.ServiceRequester;

public class MonitoringClient {

	private final static Logger logger = Logger.getLogger(MonitoringClient.class);

	private static Settings settings = new Settings();

	private PrintWriter outLogFile;

	private void start() {
		cleanupDownloadFolder();

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

		String image = "image_" + settings.imageType + ".jpg";

		ServiceRequester requester = new ServiceRequester(settings.serviceUrl + Constants.APP_PATH);

		logger.info("Test running. Please wait...");

		for (int i = 0; i < settings.requestCount; i++) {
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
		logger.info("Monitoring client shutdown.");
		requester.shutdown();

		if (outLogFile != null) {
			outLogFile.close();
		}

		System.exit(0);
	}

	private void cleanupDownloadFolder() {
		// delete all image files from download directory (except .gitignore)
		try {
			Files.walk(Paths.get(Constants.DOWNLOAD_PATH)).map(Path::toFile)
					.filter(f -> !f.getName().equals(".gitignore")).forEach(File::delete);
		} catch (IOException e) {
			logger.error("Error cleaning up download directory.");
		}
	}

	public static void main(final String[] args) {
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

		MonitoringClient monitoringClient = new MonitoringClient();
		monitoringClient.start();
	}
}
