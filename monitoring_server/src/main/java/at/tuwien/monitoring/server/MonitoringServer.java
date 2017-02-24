package at.tuwien.monitoring.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import at.tuwien.monitoring.server.jms.JmsService;

public class MonitoringServer {

	private final static Logger logger = Logger.getLogger(MonitoringServer.class);

	private JmsService jmsService;

	private boolean init(String jmsBrokerURL) {
		jmsService = new JmsService(jmsBrokerURL);
		jmsService.start();

		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return false;
		}
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
		jmsService.stop();
	}

	public static void main(String[] args) {
		String jmsBrokerURL = null;
		if (args.length > 1) {
			logger.error("Invalid number of arguments.");
			return;
		} else if (args.length == 1) {
			jmsBrokerURL = args[0];
		}

		MonitoringServer server = new MonitoringServer();
		if (server.init(jmsBrokerURL)) {
			server.start();
		}
	}
}