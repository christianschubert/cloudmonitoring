package at.tuwien.monitoring.integration;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

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

	private Set<Application> applications = new HashSet<>();

	public Integration(Settings settings) {
		this.settings = settings;
	}

	private void init() {
		// start ttp
		monitoringServer = new MonitoringServer();
		if (monitoringServer.init()) {
			// add sample agreement
			monitoringServer.startSLAMonitoring(settings.etcFolderPath + "/image_service_agreement.xml");
		} else {
			logger.error("Error initializing monitoring server.");
			monitoringServer.shutdown();
			return;
		}

		// start agent
		monitoringAgent = new MonitoringAgent(settings);
		if (monitoringAgent.init()) {
			// add n applications to monitor
			startApplications(3);
		} else {
			logger.error("Error initializing monitoring agent.");
			monitoringAgent.shutdown();
			return;
		}

		// TODO: do something with applications

		// stop monitoring of applications
		for (Application application : applications) {
			monitoringAgent.stopApplicationMonitoring(application);
		}

		// stop agent
		monitoringAgent.shutdown();

		// stop ttp
		monitoringServer.shutdown();
	}

	private void startApplications(int count) {
		for (int i = 0; i < count; i++) {
			Application application = new Application(APP_PATH, String.valueOf(8070 + i),
					EnumSet.of(MonitorTask.Cpu, MonitorTask.Memory));

			monitoringAgent.startApplicationMonitoring(application, true);
			applications.add(application);
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