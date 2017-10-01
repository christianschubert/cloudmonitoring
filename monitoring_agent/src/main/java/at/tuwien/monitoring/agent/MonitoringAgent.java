package at.tuwien.monitoring.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.common.GlobalConstants;
import at.tuwien.common.Settings;
import at.tuwien.common.Utils;
import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.extension.ExtensionServer;
import at.tuwien.monitoring.agent.process.ProcessTools;
import at.tuwien.monitoring.jms.JmsSenderService;
import at.tuwien.monitoring.jms.messages.MetricAggregationMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private Settings settings;

	private AtomicInteger currentApplicationID = new AtomicInteger(0);

	private Sigar sigar;
	private int cpuCount;
	private long memTotal;
	private String publicIPAddress;

	private JmsSenderService jmsService;

	private ExtensionServer extensionServer;

	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> scheduledJmsSender;

	private List<ApplicationMonitor> applicationList = Collections.synchronizedList(new ArrayList<>());

	public MonitoringAgent(Settings settings) {
		this.settings = settings;
	}

	public boolean init() {
		if (!checkWeaver()) {
			return false;
		}

		if (!initSigar()) {
			return false;
		}

		publicIPAddress = Utils.lookupPublicIPAddress();

		jmsService = new JmsSenderService(settings.brokerUrl, GlobalConstants.QUEUE_AGENTS);
		jmsService.start();
		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return false;
		}

		extensionServer = new ExtensionServer();
		if (!extensionServer.start()) {
			logger.error("Error starting extension server.");
			return false;
		}

		scheduler = Executors.newScheduledThreadPool(1);
		scheduledJmsSender = scheduler.scheduleAtFixedRate(new JmsMetricSenderTask(), 0,
				settings.metricsAggregationInterval, TimeUnit.MILLISECONDS);

		return true;
	}

	private boolean checkWeaver() {
		if (!new File(Constants.ASPECTJ_WEAVER_PATH).exists()) {
			logger.error(
					"AspectJ Weaver not found. Make sure that the folder \"aspectjweaver\" is in the same folder as the executable.");
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

		System.setProperty("org.hyperic.sigar.path", "sigarnatives");
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

			memTotal = sigar.getMem().getTotal();
			logger.info("Total memory [Byte]: " + memTotal);

		} catch (SigarException e) {
			logger.error("Error retrieving cpu count or memory size.");
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

	public long startApplicationMonitoring(Application application, boolean isJavaAplication) {
		if (!new File(application.getApplicationPath()).exists()) {
			logger.error("Application " + application.getApplicationPath() + " does not exist. Ignoring application.");
			return -1;
		}

		List<String> applicationWithParams = new ArrayList<>();
		if (isJavaAplication) {
			applicationWithParams.add("java");
			applicationWithParams.add("-javaagent:" + Constants.ASPECTJ_WEAVER_PATH);
			applicationWithParams.add("-jar");
		}
		applicationWithParams.add(application.getApplicationPath());
		applicationWithParams.addAll(application.getParams());

		ApplicationMonitor applicationMonitor = new ApplicationMonitor(cpuCount, memTotal, applicationWithParams,
				application, settings, currentApplicationID.incrementAndGet());
		long pid = applicationMonitor.start();
		if (pid != -1) {
			applicationList.add(applicationMonitor);
		}

		return pid;
	}

	public void stopApplicationMonitoring(Application application) {
		synchronized (applicationList) {
			Iterator<ApplicationMonitor> itAppList = applicationList.iterator();
			while (itAppList.hasNext()) {
				ApplicationMonitor applicationMonitor = itAppList.next();
				if (applicationMonitor.getApplication().equals(application)) {
					applicationMonitor.stop();
					itAppList.remove();
					return;
				}
			}
		}
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
							message.setIpAddress(publicIPAddress);
							aggregationMessage.addMetricMessage(message);
						}
					} else {
						// Monitoring stopped -> remove from watchlist
						itAppList.remove();
					}
				}
			}

			// add messages from extension server
			if (extensionServer != null) {
				Queue<MetricMessage> extensionMetric = extensionServer.getCollectedMetrics();
				MetricMessage message = null;
				while ((message = extensionMetric.poll()) != null) {
					message.setIpAddress(publicIPAddress);
					aggregationMessage.addMetricMessage(message);
				}
			}

			// send aggregated message
			if (!aggregationMessage.getMessageList().isEmpty()) {
				jmsService.sendObjectMessage(aggregationMessage);
			}
			aggregationMessage = null;
		}
	}

	public void shutdown() {
		logger.info("Shutting down agent...");

		if (extensionServer != null) {
			extensionServer.stop();
		}

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

		if (jmsService != null) {
			jmsService.stop();
		}

		// stop all monitorings and their processes
		synchronized (applicationList) {
			for (ApplicationMonitor applicationMonitor : applicationList) {
				applicationMonitor.stop();
			}
		}

		closeSigar();

		logger.info("Shut down success");
	}

	public static void main(final String[] args) {
		Settings settings = Utils.parseArgsForSettings(args);
		MonitoringAgent agent = new MonitoringAgent(settings);
		if (agent.init()) {
			// for test purposes monitor imageresizer application only
			Application imageResizer = new Application(
					"../monitoring_service/target/monitoring_service-0.0.1-SNAPSHOT-jar-with-dependencies.jar",
					Arrays.asList("-p8080"), EnumSet.of(MonitorTask.Cpu, MonitorTask.Memory));

			agent.startApplicationMonitoring(imageResizer, true);

			// monitor till user hits RETURN
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

			agent.shutdown();
		} else {
			agent.shutdown();
		}
	}
}