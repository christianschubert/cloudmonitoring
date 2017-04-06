package at.tuwien.monitoring.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import at.tuwien.monitoring.server.http.EmbeddedHttpServer;
import at.tuwien.monitoring.server.jms.JmsService;

public class MonitoringServer {

	private final static Logger logger = Logger.getLogger(MonitoringServer.class);

	private JmsService jmsService;
	private EmbeddedHttpServer httpServer;

	private boolean init(String jmsBrokerURL, boolean embeddedJmsBroker) {
		jmsService = new JmsService(jmsBrokerURL, embeddedJmsBroker);
		jmsService.start();

		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return false;
		}

		httpServer = new EmbeddedHttpServer();
		httpServer.startServer();

		return true;
	}

	private void start() {
		logger.info("Monitoring server running. Press RETURN to exit.");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Monitoring server shutdown.");
		shutdown();
	}

	private void shutdown() {
		jmsService.stop();
		httpServer.stopServer();
	}

	public static void main(String[] args) {
		String jmsBrokerURL = null;
		boolean embeddedJmsBroker = true;

		if (args.length > 2) {
			logger.error("Invalid number of arguments.");
			return;
		}

		for (int i = 0; i < args.length; i++) {
			if (args[i].contains("-prod")) {
				embeddedJmsBroker = false;
			} else {
				jmsBrokerURL = args[i];
			}
		}

		MonitoringServer server = new MonitoringServer();
		if (server.init(jmsBrokerURL, embeddedJmsBroker)) {
			server.start();
		}
	}
}