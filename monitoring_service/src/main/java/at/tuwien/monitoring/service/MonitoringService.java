package at.tuwien.monitoring.service;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Main class.
 *
 */
public class MonitoringService {

	static final int DEFAULT_PORT = 8080;

	// Base URI the Grizzly HTTP server will listen on
	private static final String BASE_URI = "http://0.0.0.0:%d/imageresizer/";

	static String getBaseUri(int port) {
		return String.format(BASE_URI, port);
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer(int port) {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in at.tuwien.monitoring.service package
		final ResourceConfig rc = new ResourceConfig().packages("at.tuwien.monitoring.service");
		rc.register(MultiPartFeature.class);

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(getBaseUri(port)), rc);
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {

		int port = DEFAULT_PORT;

		for (String arg : args) {
			try {
				int tempPort = Integer.parseInt(arg);
				if (tempPort >= 1 && tempPort <= 65535) {
					port = tempPort;
				}
				else {
					throw new IllegalArgumentException();
				}
			}
			catch (IllegalArgumentException e) {
				System.err.println("Invalid argument. Must be a valid port number (1-65535).");
			}

		}

		final HttpServer server = startServer(port);
		System.out.println(String.format("Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...", getBaseUri(port)));
		System.in.read();
		server.shutdownNow();
		System.exit(0);
	}
}
