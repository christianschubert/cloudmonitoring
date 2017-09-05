package at.tuwien.monitoring.integration;

import java.util.EnumSet;

import org.apache.log4j.Logger;

import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.agent.Application;
import at.tuwien.monitoring.agent.MonitoringAgent;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.server.MonitoringServer;

public class Integration {

	private final static Logger logger = Logger.getLogger(Integration.class);

	private static final String APP_PATH = "../monitoring_service/target/monitoring_service-0.0.1-SNAPSHOT-jar-with-dependencies.jar";

	private Settings settings;

	private MonitoringServer monitoringServer;
	private MonitoringAgent monitoringAgent;

	public Integration(Settings settings) {
		this.settings = settings;
	}

	private void init() {
		monitoringServer = new MonitoringServer();
		if (monitoringServer.init()) {
			monitoringServer.startSLAMonitoring("src/main/resources/image_service_agreement.xml");
		} else {
			logger.error("Error initializing monitoring server.");
			monitoringServer.shutdown();
			return;
		}

		monitoringAgent = new MonitoringAgent(settings);
		if (monitoringAgent.init()) {
			startApplications(5);
		} else {
			logger.error("Error initializing monitoring agent.");
			monitoringAgent.shutdown();
			return;
		}
	}

	private void startApplications(int count) {
		for (int i = 0; i < count; i++) {
			Application imageResizer = new Application(APP_PATH, String.valueOf(8070 + i),
					EnumSet.of(MonitorTask.Cpu, MonitorTask.Memory));

			monitoringAgent.startApplicationMonitoring(imageResizer, true);

			try {
				// wait for process to start
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Settings settings = new Settings();

		for (String arg : args) {
			if (!arg.startsWith("config:")) {
				continue;
			}
			String split[] = arg.split("config:");
			if (split.length != 2) {
				continue;
			}
			settings = Utils.readProperties(split[1]);
		}

		Integration integration = new Integration(settings);
		integration.init();
	}
}