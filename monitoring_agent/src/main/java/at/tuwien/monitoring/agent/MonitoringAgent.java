package at.tuwien.monitoring.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.jms.JmsService;
import at.tuwien.monitoring.agent.process.ProcessTools;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private Sigar sigar;
	private int cpuCount;
	private JmsService jmsService;

	private List<ApplicationMonitor> applicationList = new ArrayList<ApplicationMonitor>();

	private boolean init(String jmsBrokerURL) {
		if (!initSigar()) {
			return false;
		}

		String publicIPAddress = lookupPublicIPAddress();
		if (publicIPAddress == null) {
			return false;
		}

		jmsService = new JmsService(jmsBrokerURL, publicIPAddress);
		jmsService.start();
		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return false;
		}

		return true;
	}

	private boolean initSigar() {
		if (!new File("sigarnatives").exists()) {
			logger.error(
					"Sigar natives not found. Make sure that the folder \"sigarnatives\" is in the same folder as the executable.");
			return false;
		}
		System.setProperty("java.library.path", "sigarnatives");

		sigar = new Sigar();

		if (sigar.getNativeLibrary() == null) {
			logger.error("Error loading sigar native library.");
			closeSigar();
			return false;
		}

		try {
			cpuCount = sigar.getCpuList().length;
			if (cpuCount < 1) {
				throw new SigarException();
			}

			logger.info("CPU count: " + cpuCount);

		} catch (SigarException e) {
			logger.error("Error retrieving cpu count.");
			closeSigar();
			return false;
		}

		ProcessTools.setSigar(sigar);
		return true;
	}

	private void closeSigar() {
		if (sigar != null) {
			sigar.close();
			sigar = null;
		}
	}

	private String lookupPublicIPAddress() {
		BufferedReader reader = null;
		try {
			URL checkIP = new URL("http://checkip.amazonaws.com");
			reader = new BufferedReader(new InputStreamReader(checkIP.openStream()));
			return reader.readLine();
		} catch (IOException e) {
			logger.error("Error looking up public IP address.");
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private void startMonitoring(String[] applicationWithParams, List<MonitorTask> monitorTasks) {
		ApplicationMonitor applicationMonitor = new ApplicationMonitor(sigar, cpuCount, applicationWithParams,
				monitorTasks);
		applicationMonitor.start();
		applicationList.add(applicationMonitor);

		// jmsService.sendObjectMessage(
		// new CpuLoadMessage("java", new Date().getTime(),
	}

	private void stopAll() {
		// stop all monitorings and their processes
		for (ApplicationMonitor applicationMonitor : applicationList) {
			applicationMonitor.stop();
		}

		jmsService.stop();
		closeSigar();
	}

	public static void main(String[] args) {
		String jmsBrokerURL = null;
		if (args.length > 1) {
			logger.error("Invalid number of arguments.");
			return;
		} else if (args.length == 1) {
			jmsBrokerURL = args[0];
		}

		MonitoringAgent agent = new MonitoringAgent();
		if (agent.init(jmsBrokerURL)) {

			// for test purposes monitor imageresizer application only
			String applicationPath = "../imageresizer/target/imageresizer-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
			String[] applicationWithParams = new String[] { "java", "-jar", applicationPath };

			// monitor cpu load of application
			agent.startMonitoring(applicationWithParams, Arrays.asList(MonitorTask.CpuLoad));
		}
	}
}