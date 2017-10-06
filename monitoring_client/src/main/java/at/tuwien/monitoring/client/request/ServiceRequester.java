package at.tuwien.monitoring.client.request;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import at.tuwien.common.Settings;
import at.tuwien.monitoring.client.constants.Constants;

public class ServiceRequester {

	private final static Logger logger = Logger.getLogger(ServiceRequester.class);

	private static int requestID = 0;

	private WebTarget target;
	private Client client;

	private Settings settings;
	private PrintWriter outLogFile;

	public ServiceRequester(String serviceURI, Settings settings) {
		this.settings = settings;

		client = ClientBuilder.newClient();
		client.register(MultiPartFeature.class);
		target = client.target(serviceURI);

		if (settings.logMetrics) {
			try {
				FileWriter fw = new FileWriter(settings.etcFolderPath + "/logs/logs_client_responsetime.csv");
				BufferedWriter bw = new BufferedWriter(fw);
				outLogFile = new PrintWriter(bw);

				// csv header
				outLogFile.println("responseTime");
			} catch (IOException e) {
				settings.logMetrics = false;
				logger.error("Error logging metrics to file.");
			}
		}
	}

	public void shrinkRequest(String image, int size) {
		shrinkRequest(image, size, Rotation.NONE);
	}

	public void shrinkRequest(String image, Rotation rotation) {
		shrinkRequest(image, 0, rotation);
	}

	public void shrinkRequest(String image, int size, Rotation rotation) {
		int currentRequestID = ++requestID;

		// start measuring response time from client - to compare with aspect
		long startTime = System.nanoTime();

		Response response = null;
		try {
			response = target.request().post(buildPostEntity(image, size, rotation));
		} catch (Throwable e) {
			logger.error("Error processing request with ID " + currentRequestID + ". Server offline?");
			return;
		}

		if (response.getStatus() != Response.Status.OK.getStatusCode()) {
			logger.error("Service status code is not ok. Code: " + response.getStatus() + "; (Request ID "
					+ currentRequestID + ")");
			return;
		}

		if (settings.logMetrics) {
			long responseTime = System.nanoTime() - startTime;
			outLogFile.println(TimeUnit.NANOSECONDS.toMillis(responseTime));
			outLogFile.flush();
		}

		long startTimeRead = System.nanoTime();

		InputStream is = response.readEntity(InputStream.class);
		try {
			Files.copy(is, Paths.get(Constants.DOWNLOAD_PATH + image), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			logger.error("Error while copying shrinked image (Request ID " + currentRequestID + ").");
		}

		System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeRead));

		response.close();
	}

	private Entity<FormDataMultiPart> buildPostEntity(String image, int size, Rotation rotation) {
		FileDataBodyPart filePart = new FileDataBodyPart("image", new File(Constants.UPLOAD_PATH + image));
		filePart.setContentDisposition(FormDataContentDisposition.name("image").fileName(image).build());

		FormDataMultiPart multipartEntity = new FormDataMultiPart();
		multipartEntity.bodyPart(filePart);

		if (size > 0) {
			multipartEntity.field("size", String.valueOf(size));
		}

		if (rotation != Rotation.NONE) {
			multipartEntity.field("rotation", rotation.toString());
		}

		return Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA);
	}

	public void shutdown() {
		if (outLogFile != null) {
			outLogFile.close();
		}

		if (client != null) {
			client.close();
		}
	}
}
