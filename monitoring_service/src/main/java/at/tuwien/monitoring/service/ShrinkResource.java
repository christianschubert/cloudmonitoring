package at.tuwien.monitoring.service;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
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
@Path("shrink")
public class ShrinkResource {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpg", "image/png", "image/gif", MediaType.APPLICATION_JSON })
	@ManagedAsync
	public void uploadImage(@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition detail, @FormDataParam("size") int size,
			@FormDataParam("width") int width, @FormDataParam("height") int height,
			@FormDataParam("rotation") String rotation, @Suspended final AsyncResponse asyncResponse)
			throws IOException {

		asyncResponse.setTimeoutHandler(new TimeoutHandler() {
			@Override
			public void handleTimeout(AsyncResponse asyncResponse) {
				asyncResponse.resume(
						Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
			}
		});
		asyncResponse.setTimeout(20, TimeUnit.SECONDS);

		if (uploadedInputStream == null || detail == null) {
			asyncResponse.resume(
					Response.status(Response.Status.BAD_REQUEST).entity(new Message("No image provided.")).build());
			return;
		}

		String extension = detail.getFileName().substring(detail.getFileName().lastIndexOf(".") + 1);
		if (!(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png")
				|| extension.toLowerCase().equals("gif"))) {
			asyncResponse.resume(Response.status(Response.Status.BAD_REQUEST)
					.entity(new Message("Invalid image extension. (Use *.jpg, *.png or *.gif)")).build());
			return;
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

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// return source image if no parameters for resizing where given
			ImageIO.write(resized != null ? resized : src, extension, baos);
			byte[] imageData = baos.toByteArray();

			asyncResponse.resume(Response.ok(new ByteArrayInputStream(imageData)).build());
			return;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ImagingOpException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		asyncResponse.resume(
				Response.status(Response.Status.BAD_REQUEST).entity(new Message("Error resizing image.")).build());
	}
}
