package at.tuwien.monitoring.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.jms.JmsService;
import at.tuwien.monitoring.agent.process.ProcessTools;
import at.tuwien.monitoring.jms.messages.MetricAggregationMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private Sigar sigar;
	private int cpuCount;

	private JmsService jmsService;

	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> scheduledJmsSender;

	private List<ApplicationMonitor> applicationList = Collections
			.synchronizedList(new ArrayList<ApplicationMonitor>());

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

		scheduler = Executors.newScheduledThreadPool(1);
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

	private void start() {
		scheduledJmsSender = scheduler.scheduleAtFixedRate(new JmsMetricSenderTask(), 0,
				Constants.JMS_SEND_METRIC_MESSAGES_INTERVAL, TimeUnit.MILLISECONDS);

		logger.info("Agent started");

		// for test purposes monitor imageresizer application only
		String applicationPath = "../monitoring_service/target/monitoring_service-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
		String[] applicationWithParams = new String[] { "java", "-jar", applicationPath };

		// monitor cpu load of application
		startMonitoring(applicationWithParams, Arrays.asList(MonitorTask.CpuLoad, MonitorTask.Memory));

		// monitor till user hits RETURN
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		stopAll();
	}

	public class JmsMetricSenderTask implements Runnable {
		@Override
		public void run() {
			MetricAggregationMessage aggregationMessage = new MetricAggregationMessage();

			// aggregate all messages into one message -> collect all queues
			// from the running applications
			synchronized (applicationList) {
				Iterator<ApplicationMonitor> itAppList = applicationList.iterator();
				while (itAppList.hasNext()) {
					ApplicationMonitor applicationMonitor = itAppList.next();
					if (applicationMonitor.isMonitoring()) {
						Queue<MetricMessage> metrics = applicationMonitor.getCollectedMetrics();
						MetricMessage message = null;
						while ((message = metrics.poll()) != null) {
							aggregationMessage.addMetricMessage(message);
						}
					} else {
						// Monitoring stopped -> remove from watchlist
						itAppList.remove();
					}
				}
			}

			if (!aggregationMessage.getMessageList().isEmpty()) {
				jmsService.sendObjectMessage(aggregationMessage);
			}
			aggregationMessage = null;
		}
	}

	private void startMonitoring(String[] applicationWithParams, List<MonitorTask> monitorTasks) {
		ApplicationMonitor applicationMonitor = new ApplicationMonitor(cpuCount, applicationWithParams, monitorTasks);
		applicationMonitor.start();
		applicationList.add(applicationMonitor);
	}

	private void stopAll() {
		logger.info("Shutting down agent...");

		if (scheduledJmsSender != null) {
			scheduledJmsSender.cancel(true);
		}
		if (scheduler != null) {
			scheduler.shutdown();
			try {
				scheduler.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				scheduler.shutdownNow();
			}
		}

		jmsService.stop();

		// stop all monitorings and their processes
		synchronized (applicationList) {
			for (ApplicationMonitor applicationMonitor : applicationList) {
				applicationMonitor.stop();
			}
		}

		closeSigar();

		logger.info("Shut down success");
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