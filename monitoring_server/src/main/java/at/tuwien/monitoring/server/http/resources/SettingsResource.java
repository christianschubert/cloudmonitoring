package at.tuwien.monitoring.server.http.resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("settings")
public class SettingsResource {

	@Context
	private Application app;

	private String settingsPath;

	@PostConstruct
	public void postConstruct() {
		Map<String, Object> properties = app.getProperties();
		if (properties != null && !properties.isEmpty()) {
			settingsPath = (String) properties.get("settings.path");
		}
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response getSettings() {
		try {
			String settings = new String(Files.readAllBytes(Paths.get(settingsPath)));
			return Response.ok().entity(settings).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}