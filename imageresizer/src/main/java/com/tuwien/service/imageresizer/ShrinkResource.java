package com.tuwien.service.imageresizer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.imgscalr.Scalr.Rotation;

import com.tuwien.service.imageresizer.pojo.Message;

/**
 * Root resource (exposed at "shrink" path)
 */
@Path("shrink")
public class ShrinkResource {

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "image/jpg", "image/png", "image/gif", MediaType.APPLICATION_JSON })
	public Response uploadImage(@FormDataParam("image") InputStream uploadedInputStream,
			@FormDataParam("image") FormDataContentDisposition detail, @FormDataParam("size") int size,
			@FormDataParam("width") int width, @FormDataParam("height") int height,
			@FormDataParam("rotation") String rotation) throws IOException {

		if (uploadedInputStream == null || detail == null) {
			return Response.ok(new Message("No image provided.")).build();
		}

		String extension = detail.getFileName().substring(detail.getFileName().lastIndexOf(".") + 1);
		if (!(extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("png")
				|| extension.toLowerCase().equals("gif"))) {
			return Response.ok(new Message("Invalid image extension. (Use *.jpg, *.png or *.gif)")).build();
		}

		try {
			BufferedImage src = ImageIO.read(uploadedInputStream);
			BufferedImage resized = null;

			if (width > 0 && height > 0) {
				resized = Scalr.resize(src, Mode.FIT_EXACT, width, height);
			} else if (size > 0) {
				resized = Scalr.resize(src, size);
			}

			if (rotation != null && !rotation.trim().isEmpty()) {
				try {
					Rotation rot = Rotation.valueOf(rotation);
					resized = Scalr.rotate(resized != null ? resized : src, rot, Scalr.OP_ANTIALIAS);
				} catch (IllegalArgumentException e) {
					System.err.println("Invalid parameter for rotation. Ignoring rotation.");
				}
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// return source image if no parameters for resizing where given
			ImageIO.write(resized != null ? resized : src, extension, baos);
			byte[] imageData = baos.toByteArray();

			return Response.ok(new ByteArrayInputStream(imageData)).build();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.ok(new Message("Error resizing image.")).build();
	}
}
