package at.tuwien.monitoring.integration;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.agent.Application;
import at.tuwien.monitoring.agent.MonitoringAgent;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.client.MonitoringClient;
import at.tuwien.monitoring.server.MonitoringServer;

public class Integration {

	private final static Logger logger = Logger.getLogger(Integration.class);

	private static final String APP_PATH = "../monitoring_service/target/monitoring_service-0.0.1-SNAPSHOT-jar-with-dependencies.jar";

	private Settings settings;

	private MonitoringAgent monitoringAgent;

	private Set<Application> applications = new HashSet<>();

	public Integration(Settings settings) {
		this.settings = settings;

		// this is a local integration test ->
		// use (localhost) default urls for broker and service
		Settings defaultSettings = new Settings();
		this.settings.brokerUrl = defaultSettings.brokerUrl;
		this.settings.serviceUrl = defaultSettings.serviceUrl;
	}

	private void init() {
		// start ttp
		MonitoringServer monitoringServer = new MonitoringServer(settings);
		if (!monitoringServer.init()) {
			logger.error("Error initializing monitoring server.");
			monitoringServer.shutdown();
			return;
		}

		String wslaFilePath = settings.etcFolderPath + "/agreements/" + settings.wslaFile;
		if (!new File(wslaFilePath).exists()) {
			logger.error("Specified WLSA file does not exist. Shutdown.");
			monitoringServer.shutdown();
			return;
		}

		// start agent
		monitoringAgent = new MonitoringAgent(settings);
		if (!monitoringAgent.init()) {
			logger.error("Error initializing monitoring agent.");
			monitoringAgent.shutdown();
			return;

		}

		// start client and run tests
		MonitoringClient monitoringClient = new MonitoringClient(settings);
		monitoringClient.init();

		startApplications(1, 8080);

		// add specified agreement to server
		monitoringServer.startSLAMonitoring(wslaFilePath);

		monitoringClient.runTest();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// stop client
		monitoringClient.shutdown();

		// stop monitoring of applications
		for (Application application : applications) {
			monitoringAgent.stopApplicationMonitoring(application);
		}

		// stop agent
		monitoringAgent.shutdown();

		// stop ttp
		monitoringServer.shutdown();

		System.exit(0);
	}

	private void startApplications(int count, int firstPort) {
		for (int i = 0; i < count; i++) {
			List<String> params = Arrays.asList("-p" + (firstPort + i));
			if (new File(settings.etcFolderPath + "/settings.properties").exists()) {
				params = Arrays.asList("config:" + settings.etcFolderPath + "/settings.properties",
						" -p" + (firstPort + i));
			}

			Application application = new Application(APP_PATH, params,
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
		Settings settings = Utils.parseArgsForSettings(args);
		Integration integration = new Integration(settings);
		integration.init();
	}
}