package at.tuwien.monitoring.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.jms.JmsService;
import at.tuwien.monitoring.agent.process.ProcessRunner;
import at.tuwien.monitoring.agent.process.ProcessTools;
import at.tuwien.monitoring.jms.messages.CpuLoadMessage;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private Sigar sigar;
	private JmsService jmsService;

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
			return false;
		}

		ProcessTools.setSigar(sigar);
		return true;
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

	private void start() {
		ProcessRunner processRunner = new ProcessRunner("java", "-jar", Constants.RESIZER_PATH);
		long pid = processRunner.start();

		while (true) {

			Set<Long> processesToMonitor = ProcessTools.findProcessesToMonitor(pid);
			// System.out.println(processesToMonitor);

			try {
				jmsService.sendObjectMessage(
						new CpuLoadMessage("java", new Date().getTime(), sigar.getProcCpu(pid).getPercent()));
			} catch (SigarException e1) {
				e1.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
			agent.start();
		}
	}
}