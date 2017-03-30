package at.tuwien.monitoring.service;

import static org.junit.Assert.assertEquals;

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

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ShrinkTest {

	private static final String UPLOAD_PATH = "upload/";
	private static final String DOWNLOAD_PATH = "download/";
	private static final String IMAGE = "image_medium.jpg";

	private HttpServer server;
	private WebTarget target;
	private Client client;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = MonitoringService.startServer();

		// create the client
		client = ClientBuilder.newClient();
		client.register(MultiPartFeature.class);
		target = client.target(MonitoringService.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	@Test
	public void testShrink() throws IOException {
		FileDataBodyPart filePart = new FileDataBodyPart("image", new File(UPLOAD_PATH + IMAGE));
		filePart.setContentDisposition(FormDataContentDisposition.name("image").fileName(IMAGE).build());

		FormDataMultiPart multipartEntity = new FormDataMultiPart();
		multipartEntity.bodyPart(filePart);
		multipartEntity.field("size", "300");
		multipartEntity.field("rotation", "CW_90");

		Response response = target.path("shrink").request()
				.post(Entity.entity(multipartEntity, MediaType.MULTIPART_FORM_DATA));

		int status = response.getStatus();
		InputStream is = response.readEntity(InputStream.class);

		Files.copy(is, Paths.get(DOWNLOAD_PATH + IMAGE), StandardCopyOption.REPLACE_EXISTING);

		assertEquals(Response.Status.OK.getStatusCode(), status);

		response.close();
	}
}
