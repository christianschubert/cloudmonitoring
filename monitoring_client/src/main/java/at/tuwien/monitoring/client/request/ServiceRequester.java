package at.tuwien.monitoring.client.request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import at.tuwien.monitoring.client.constants.Constants;

public class ServiceRequester {

	private final static Logger logger = Logger.getLogger(ServiceRequester.class);

	private static int requestID = 0;

	private WebTarget target;
	private Client client;

	public ServiceRequester(String serviceURI) {
		client = ClientBuilder.newClient();
		client.register(MultiPartFeature.class);
		target = client.target(serviceURI);
	}

	public void shrinkRequest(String image, int size) {
		shrinkRequest(image, size, Rotation.NONE);
	}

	public void shrinkRequest(String image, Rotation rotation) {
		shrinkRequest(image, 0, rotation);
	}

	public void shrinkRequest(String image, int size, Rotation rotation) {

		int currentRequestID = ++requestID;
		logger.info("New request: ID=" + currentRequestID + "; image=" + image + "; size=" + size + "; rotation="
				+ rotation.toString());

		target.request().async().post(buildPostEntity(image, size, rotation), new InvocationCallback<Response>() {

			@Override
			public void completed(Response response) {
				if (response.getStatus() != Response.Status.OK.getStatusCode()) {
					logger.error("Service status code is not ok. Code: " + response.getStatus() + "; (Request ID "
							+ currentRequestID + ")");
					return;
				}

				InputStream is = response.readEntity(InputStream.class);
				try {
					Files.copy(is, Paths.get(Constants.DOWNLOAD_PATH + currentRequestID + "_" + image),
							StandardCopyOption.REPLACE_EXISTING);
					logger.info("Request with ID " + currentRequestID + " finished!");

				} catch (IOException e) {
					logger.error("Error while copying shrinked image (Request ID " + currentRequestID + ").");
				}
			}

			@Override
			public void failed(Throwable throwable) {
				logger.error("Error processing request with ID " + currentRequestID + ". Server offline?");
			}
		});

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
		if (client != null) {
			client.close();
		}
	}
}
