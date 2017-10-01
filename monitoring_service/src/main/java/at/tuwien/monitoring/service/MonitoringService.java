package at.tuwien.monitoring.service;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
		return startServer(port, 0, 0);
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer(int port, double responseTimeInjectionRate, int expectedRequests) {
		// create a resource config that scans for JAX-RS resources and
		// providers
		// in at.tuwien.monitoring.service package
		final ResourceConfig rc = new ResourceConfig().packages("at.tuwien.monitoring.service");
		rc.register(MultiPartFeature.class);

		if (responseTimeInjectionRate != 0) {
			System.out.println(String.format(
					"Service has enabled intended response time delay (Rate: %f, Total expected requests: %d)",
					responseTimeInjectionRate, expectedRequests));
			Map<String, Object> properties = new HashMap<>();
			properties.put("delay.every.x.request",
					(int) (expectedRequests / (expectedRequests * responseTimeInjectionRate)));
			rc.setProperties(properties);
		}

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
		double rate = 0.0d;
		int expectedRequests = 0;

		for (String arg : args) {
			if (arg.startsWith("-d")) {
				// longer response time injection rate
				String rateArg = arg.split("-d")[1];
				try {
					rate = Double.parseDouble(rateArg);
				} catch (NumberFormatException e) {
					System.err.println("Invalid argument for response time delay rate.");
				}
			} else if (arg.startsWith("-t")) {
				// total expected requests (for response time rate)
				String totalArg = arg.split("-t")[1];
				try {
					expectedRequests = Integer.parseInt(totalArg);
				} catch (NumberFormatException e) {
					System.err.println("Invalid argument for total expected requests.");
				}
			} else {
				// port
				try {
					int tempPort = Integer.parseInt(arg);
					if (tempPort >= 1 && tempPort <= 65535) {
						port = tempPort;
					} else {
						throw new IllegalArgumentException();
					}
				} catch (IllegalArgumentException e) {
					System.err.println("Invalid argument. Must be a valid port number (1-65535).");
				}
			}
		}

		final HttpServer server = startServer(port, rate, expectedRequests);
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				getBaseUri(port)));
		System.in.read();
		server.shutdownNow();
	}
}
