package at.tuwien.monitoring.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import at.tuwien.common.Method;
import at.tuwien.monitoring.client.aspect.MonitorRequest;
import at.tuwien.monitoring.client.constants.Constants;
import at.tuwien.monitoring.client.request.Rotation;
import at.tuwien.monitoring.client.request.ServiceRequester;

public class MonitoringClient {

	private final static Logger logger = Logger.getLogger(MonitoringClient.class);

	private void start() {
		cleanupDownloadFolder();
		logger.info("Monitoring client running. Press RETURN to exit.");

		ServiceRequester requester = new ServiceRequester(Constants.SERVICE_URI_LOCAL);
		requester.shrinkRequest(Constants.IMAGE_MEDIUM, 300);
		requester.shrinkRequest(Constants.IMAGE_SMALL, Rotation.CW_90);

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Monitoring client shutdown.");
		requester.shutdown();
	}

	@MonitorRequest(method = Method.GET, target = Constants.SERVICE_URI_LOCAL)
	private void testAnnotation() {
		System.out.println("Test");
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

	public static void main(String[] args) {
		MonitoringClient monitoringClient = new MonitoringClient();
		monitoringClient.start();
	}
}
