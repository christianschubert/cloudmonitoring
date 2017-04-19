package at.tuwien.monitoring.server;

import java.io.IOException;

import org.apache.log4j.Logger;

import at.tuwien.monitoring.server.db.dao.ViolationDAO;
import at.tuwien.monitoring.server.http.EmbeddedHttpServer;
import at.tuwien.monitoring.server.jms.JmsReceiverService;
import at.tuwien.monitoring.server.processing.MetricProcessor;
import at.tuwien.monitoring.server.processing.mapping.Wsla2ExpressionMapper;
import at.tuwien.monitoring.server.wsla.WebServiceLevelAgreement;

public class MonitoringServer {

	private final static Logger logger = Logger.getLogger(MonitoringServer.class);

	private JmsReceiverService jmsService;
	private EmbeddedHttpServer httpServer;
	private MetricProcessor metricProcessor;

	private boolean init(final String jmsBrokerURL, final boolean embeddedJmsBroker) {

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
		// delete database data
		new ViolationDAO().deleteAll();

		WebServiceLevelAgreement wsla = new WebServiceLevelAgreement("src/main/resources/image_service_agreement.xml");
		if (!wsla.isValid()) {
			return;
		}

		String info = String.format("New Agreement \"%s\" [Consumer: %s, Provider: %s]", wsla.getWSLA().getName(),
				wsla.getWSLA().getParties().getServiceConsumer().getName(),
				wsla.getWSLA().getParties().getServiceProvider().getName());
		logger.info(info);

		new Wsla2ExpressionMapper(wsla, metricProcessor);

		// send test events
		// metricProcessor
		// .addEvent(new ClientResponseTimeMessage("127.0.0.1", new Date(),
		// "shrink", Method.GET, 2800, 400));
		// metricProcessor
		// .addEvent(new ClientResponseTimeMessage("127.0.0.1", new Date(),
		// "shrink", Method.GET, 2000, 400));
		// metricProcessor
		// .addEvent(new ClientResponseTimeMessage("127.0.0.1", new Date(),
		// "shrink", Method.GET, 2000, 400));
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

	public static void main(final String[] args) {
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