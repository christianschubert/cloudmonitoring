package at.tuwien.monitoring.server.http;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import at.tuwien.common.GlobalConstants;

public class EmbeddedHttpServer {

	private HttpServer httpServer;

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public void startServer() {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in at.tuwien.monitoring.service package
		final ResourceConfig rc = new ResourceConfig().packages("at.tuwien.monitoring.server.http.resources");

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(GlobalConstants.BASE_URI_HTTP_MONITORING_SERVER),
				rc);
	}

	public void stopServer() {
		httpServer.shutdownNow();
	}
}
