package at.tuwien.monitoring.server.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ProcessingException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import at.tuwien.common.GlobalConstants;
import at.tuwien.common.Settings;

public class EmbeddedHttpServer {

	private HttpServer httpServer;

	private Settings settings;

	public EmbeddedHttpServer(Settings settings) {
		this.settings = settings;
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public boolean start() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in at.tuwien.monitoring.service package
		final ResourceConfig rc = new ResourceConfig().packages("at.tuwien.monitoring.server.http.resources");
		Map<String, Object> properties = new HashMap<>();
		properties.put("settings.path", settings.etcFolderPath + "/settings.properties");
		rc.setProperties(properties);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		try {
			httpServer = GrizzlyHttpServerFactory
					.createHttpServer(URI.create(GlobalConstants.BASE_URI_HTTP_MONITORING_SERVER), rc);
			return true;
		} catch (ProcessingException e) {
			e.printStackTrace();
			stop();
		}
		return false;
	}

	public void stop() {
		if (httpServer != null) {
			httpServer.shutdownNow();
		}
	}
}
