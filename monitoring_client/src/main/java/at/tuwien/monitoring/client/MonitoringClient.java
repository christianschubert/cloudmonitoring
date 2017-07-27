package at.tuwien.monitoring.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.client.constants.Constants;
import at.tuwien.monitoring.client.request.Rotation;
import at.tuwien.monitoring.client.request.ServiceRequester;

public class MonitoringClient {

	private final static Logger logger = Logger.getLogger(MonitoringClient.class);

	private void start(String serviceUrl) {
		cleanupDownloadFolder();
		logger.info("Monitoring client running. Enter 'x' to exit. Press return to start new requests.");

		ServiceRequester requester = new ServiceRequester(serviceUrl + Constants.APP_PATH);

		boolean isRequest = true;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (isRequest) {
			requester.shrinkRequest(Constants.IMAGE_BIG, 400, Rotation.FLIP_HORZ);
			requester.shrinkRequest(Constants.IMAGE_MEDIUM, 300);
			requester.shrinkRequest(Constants.IMAGE_SMALL, Rotation.CW_90);

			try {
				if (br.readLine().equals("x")) {
					isRequest = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				isRequest = false;
			}
		}

		logger.info("Monitoring client shutdown.");
		requester.shutdown();
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

		String serviceUrl = Constants.DEFAULT_SERVICE_URL;

		for (String arg : args) {
			if (!arg.startsWith("config:")) {
				continue;
			}
			String split[] = arg.split("config:");
			if (split.length != 2) {
				continue;
			}
			Settings settings = Utils.readProperties(split[1]);
			serviceUrl = settings.serviceUrl;
		}

		MonitoringClient monitoringClient = new MonitoringClient();
		monitoringClient.start(serviceUrl);
	}
}
