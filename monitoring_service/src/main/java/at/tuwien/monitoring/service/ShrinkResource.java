package at.tuwien.monitoring.service;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.ManagedAsync;
import org.imgscalr.AsyncScalr;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.imgscalr.Scalr.Rotation;

import at.tuwien.monitoring.service.message.Message;

/**
 * Root resource (exposed at "shrink" path)
 */
@Singleton
@Path("shrink")
public class ShrinkResource {

	@Context
	private Application app;

	private int currentRequest = 0;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpg", "image/png", "image/gif", MediaType.APPLICATION_JSON })
	@ManagedAsync
	public Response uploadImage(@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition detail, @FormDataParam("size") int size,
			@FormDataParam("width") int width, @FormDataParam("height") int height,
			@FormDataParam("rotation") String rotation) throws IOException {

		if (uploadedInputStream == null || detail == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new Message("No image provided.")).build();
		}

		String extension = detail.getFileName().substring(detail.getFileName().lastIndexOf(".") + 1);
		if (!(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png")
				|| extension.toLowerCase().equals("gif"))) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity(new Message("Invalid image extension. (Use *.jpg, *.png or *.gif)")).build();
		}

		try {
			BufferedImage src = ImageIO.read(uploadedInputStream);
			BufferedImage resized = null;

			if (width > 0 && height > 0) {
				resized = AsyncScalr.resize(src, Mode.FIT_EXACT, width, height).get();
			} else if (size > 0) {
				resized = AsyncScalr.resize(src, size).get();
			}

			if (rotation != null && !rotation.trim().isEmpty()) {
				try {
					Rotation rot = Rotation.valueOf(rotation);
					resized = AsyncScalr.rotate(resized != null ? resized : src, rot, Scalr.OP_ANTIALIAS).get();
				} catch (IllegalArgumentException e) {
					System.err.println("Invalid parameter for rotation. Ignoring rotation.");
				}
			}

			// add intended response time delay for tests
			checkAddDelay();

			// add intended fail for tests
			boolean shouldFail = checkAddFail();
			currentRequest++;

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// return source image if no parameters for resizing where given
			ImageIO.write(resized != null ? resized : src, extension, baos);
			byte[] imageData = baos.toByteArray();

			if (!shouldFail) {
				return Response.ok(new ByteArrayInputStream(imageData)).build();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ImagingOpException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return Response.serverError().entity(new Message("Error resizing image.")).build();
	}

	private Set<Integer> delayRequests;
	private Set<Integer> failRequests;
	private int delayTime = 2000;
	private int delayVariation = 100;

	@PostConstruct
	public void postConstruct() {
		Map<String, Object> properties = app.getProperties();
		if (properties != null && !properties.isEmpty()) {
			Integer total = (Integer) properties.get("total.requests");
			Double delayRate = (Double) properties.get("delay.rate");
			if (total != null && delayRate != null) {
				int noDelays = (int) (total.intValue() * delayRate.doubleValue());

				// generate a set of unique random numbers
				delayRequests = ThreadLocalRandom.current().ints(0, total).distinct().limit(noDelays).boxed()
						.collect(Collectors.toSet());

				System.out.println("Delays at: " + new TreeSet<>(delayRequests));
			}

			Double failRate = (Double) properties.get("fail.rate");
			if (total != null && failRate != null) {
				int noFails = (int) (total.intValue() * failRate.doubleValue());

				failRequests = ThreadLocalRandom.current().ints(0, total).distinct().limit(noFails).boxed()
						.collect(Collectors.toSet());

				System.out.println("Fails at: " + new TreeSet<>(failRequests));
			}

			Integer time = (Integer) properties.get("delay.time");
			if (time != null) {
				delayTime = time.intValue();
			}

			Integer variation = (Integer) properties.get("delay.variation");
			if (variation != null) {
				delayVariation = variation.intValue();
			}
		}
	}

	private void checkAddDelay() {
		// no setting for delay -> do not delay
		if (delayRequests == null) {
			return;
		}

		if (delayRequests.contains(currentRequest)) {
			// add delay

			// add random time from 0 to 100 to delay
			int randomAddTime = ThreadLocalRandom.current().ints(0, delayVariation).findFirst().getAsInt();

			System.out.println(String.format("!!! Intended delay (%d ms)!!!", delayTime + randomAddTime));

			try {
				Thread.sleep(delayTime + randomAddTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkAddFail() {
		if (failRequests != null && failRequests.contains(currentRequest)) {
			System.out.println("!!! Intended fail!!!");
			return true;
		}

		return false;
	}
}
