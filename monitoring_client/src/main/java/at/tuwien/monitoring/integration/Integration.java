package at.tuwien.monitoring.integration;

import at.tuwien.monitoring.agent.MonitoringAgent;
import at.tuwien.monitoring.server.MonitoringServer;

public class Integration {

	private MonitoringServer monitoringServer;
	private MonitoringAgent monitoringAgent;

	public Integration() {

	}

	private void init() {
		monitoringServer = new MonitoringServer();
		if (monitoringServer.init()) {
			monitoringServer.startSLAMonitoring("src/main/resources/image_service_agreement.xml");
		}

		monitoringAgent = new MonitoringAgent();
	}

	public static void main(String[] args) {
		Integration integration = new Integration();
		integration.init();
	}
}