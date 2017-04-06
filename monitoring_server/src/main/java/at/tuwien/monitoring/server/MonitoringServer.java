package at.tuwien.monitoring.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import at.tuwien.monitoring.server.http.EmbeddedHttpServer;
import at.tuwien.monitoring.server.jms.JmsReceiverService;
import at.tuwien.monitoring.server.processing.MetricProcessor;
import at.tuwien.monitoring.server.wsla.WebServiceLevelAgreement;

public class MonitoringServer {

	private final static Logger logger = Logger.getLogger(MonitoringServer.class);

	private JmsReceiverService jmsService;
	private EmbeddedHttpServer httpServer;
	private MetricProcessor metricProcessor;

	private boolean init(String jmsBrokerURL, boolean embeddedJmsBroker) {

		metricProcessor = new MetricProcessor();
		if (!metricProcessor.start()) {
			logger.error("Error creating metric processor.");
			return false;
		}

		jmsService = new JmsReceiverService(jmsBrokerURL, embeddedJmsBroker, metricProcessor);
		jmsService.start();
		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return false;
		}

		httpServer = new EmbeddedHttpServer();
		if (!httpServer.start()) {
			logger.error("Error starting HTTP-Server.");
			return false;
		}

		return true;
	}

	private void run() {
		logger.info("Monitoring server running. Press RETURN to exit.");

		startMonitoring();

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		shutdown();
	}

	private void startMonitoring() {
		WebServiceLevelAgreement wsla = new WebServiceLevelAgreement("src/main/resources/sample_wsla.xml");
		if (!wsla.isValid()) {
			return;
		}

		metricProcessor.addExpression("select * from ClientResponseTimeMessage having responseTime >= 2000", "<2000");
	}

	private void shutdown() {
		logger.info("Monitoring server shutdown.");

		if (metricProcessor != null) {
			metricProcessor.stop();
		}
		if (jmsService != null) {
			jmsService.stop();
		}
		if (httpServer != null) {
			httpServer.stop();
		}
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
			server.run();
		}
	}
}