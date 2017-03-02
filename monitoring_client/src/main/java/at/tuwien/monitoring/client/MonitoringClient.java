package at.tuwien.monitoring.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

public class MonitoringClient {

	private final static Logger logger = Logger.getLogger(MonitoringClient.class);

	private static final String UPLOAD_PATH = "upload/";
	private static final String DOWNLOAD_PATH = "download/";
	private static final String IMAGE = "image.jpg";

	public static final String SERVICE_URI = "http://127.0.0.1:8080/imageresizer/";

	private WebTarget target;
	private Client client;

	public MonitoringClient() {
		client = ClientBuilder.newClient();
		client.register(MultiPartFeature.class);
		target = client.target(SERVICE_URI);
	}

	private void request() {
		FileDataBodyPart filePart = new FileDataBodyPart("image", new File(UPLOAD_PATH + IMAGE));
		filePart.setContentDisposition(FormDataContentDisposition.name("image").fileName(IMAGE).build());

		FormDataMultiPart multipartEntity = new FormDataMultiPart();
		multipartEntity.bodyPart(filePart);
		multipartEntity.field("size", "300");
		multipartEntity.field("rotation", "CW_90");

		Response response = target.path("shrink").request()
				.post(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));

		int status = response.getStatus();
		if (status != Response.Status.OK.getStatusCode()) {
			logger.error("Service status code is not ok. Code: " + status);
			response.close();
			client.close();
			return;
		}

		InputStream is = response.readEntity(InputStream.class);

		try {
			Files.copy(is, Paths.get(DOWNLOAD_PATH + IMAGE), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.close();
		client.close();
	}

	public static void main(String[] args) {
		new MonitoringClient().request();
	}
}
