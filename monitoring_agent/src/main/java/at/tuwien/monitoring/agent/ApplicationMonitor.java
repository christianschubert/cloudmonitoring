package at.tuwien.monitoring.agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.common.Settings;
import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.process.ProcessRunner;
import at.tuwien.monitoring.agent.process.ProcessTools;
import at.tuwien.monitoring.jms.messages.CpuMessage;
import at.tuwien.monitoring.jms.messages.MemoryMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class ApplicationMonitor {

	private final static Logger logger = Logger.getLogger(ApplicationMonitor.class);

	private int cpuCount;
	private long memTotal;

	private Application application;
	private int applicationID;

	private ProcessRunner processRunner;

	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> scheduledMonitor;

	private Set<MonitorTask> monitorTasks;
	private Queue<MetricMessage> collectedMetrics = new ConcurrentLinkedQueue<MetricMessage>();

	private Settings settings;
	private boolean isUberAgent;

	private boolean monitoring;

	private PrintWriter cpuLogFile;
	private boolean addCsvHeaderCpu = true;

	private PrintWriter memoryLogFile;
	private boolean addCsvHeaderMem = true;

	private TopMonitor topMonitor;
	private PrintWriter topLogFile;

	public ApplicationMonitor(int cpuCount, long memTotal, List<String> applicationWithParams, Application application,
			Settings settings, boolean isUberAgent, int applicationID) {
		this.cpuCount = cpuCount;
		this.memTotal = memTotal;
		this.application = application;
		this.settings = settings;
		this.isUberAgent = isUberAgent;
		this.applicationID = applicationID;
		this.monitorTasks = application.getMonitorTasks();

		processRunner = new ProcessRunner(applicationWithParams.toArray(new String[applicationWithParams.size()]));
		scheduler = Executors.newScheduledThreadPool(2);
	}

	public long start() {
		long pid = processRunner.start();
		if (pid == -1) {
			// Error starting process and retreiving pid
			stop();
			return pid;
		}

		initLogging();

		if (settings.logUsageTop) {
			topMonitor = new TopMonitor(pid);
			scheduler.submit(topMonitor);
		}

		scheduledMonitor = scheduler.scheduleAtFixedRate(new MonitorTimerTask(pid),
				Constants.PROCESS_MONITOR_START_DELAY, settings.systemMetricsMonitorInterval, TimeUnit.MILLISECONDS);

		monitoring = true;

		logger.info("Started monitoring of application \"" + processRunner.getProcessName() + "\" with ID "
				+ applicationID);

		return pid;
	}

	private void initLogging() {
		if (settings.logMetrics) {

			String appendix = isUberAgent ? "_uber" : "";

			try {
				FileWriter fwCpu = new FileWriter(settings.etcFolderPath + "/logs/logs_agent_cpu_application_"
						+ applicationID + appendix + ".csv");
				BufferedWriter bwCpu = new BufferedWriter(fwCpu);
				cpuLogFile = new PrintWriter(bwCpu);

				FileWriter fwMem = new FileWriter(settings.etcFolderPath + "/logs/logs_agent_mem_application_"
						+ applicationID + appendix + ".csv");
				BufferedWriter bwMem = new BufferedWriter(fwMem);
				memoryLogFile = new PrintWriter(bwMem);
			} catch (IOException e) {
				settings.logMetrics = false;
				logger.error("Error logging metrics to file.");
			}
		}
		if (settings.logUsageTop) {
			try {
				FileWriter fwTop = new FileWriter(
						settings.etcFolderPath + "/logs/logs_agent_topusage_application_" + applicationID + ".csv");
				BufferedWriter bwTop = new BufferedWriter(fwTop);
				topLogFile = new PrintWriter(bwTop);
				topLogFile.println("timestamp;cpuUsagePerc;memoryUsagePerc");
				topLogFile.flush();
			} catch (IOException e) {
				settings.logUsageTop = false;
				logger.error("Error logging top usage to file.");
			}
		}
	}

	public boolean isMonitoring() {
		return monitoring;
	}

	public Application getApplication() {
		return application;
	}

	public void stop() {

		logger.info("Shutting down monitoring of application \"" + processRunner.getProcessName() + "\" with ID "
				+ applicationID + "...");

		monitoring = false;

		if (topMonitor != null) {
			topMonitor.stop();
		}

		if (scheduledMonitor != null) {
			scheduledMonitor.cancel(true);
		}
		if (scheduler != null) {
			scheduler.shutdown();
			try {
				scheduler.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				scheduler.shutdownNow();
			}
		}
		if (processRunner != null) {
			processRunner.stop();
		}

		if (memoryLogFile != null) {
			memoryLogFile.close();
		}
		if (cpuLogFile != null) {
			cpuLogFile.close();
		}
		if (topLogFile != null) {
			topLogFile.close();
		}
	}

	public Queue<MetricMessage> getCollectedMetrics() {
		return collectedMetrics;
	}

	class MonitorTimerTask implements Runnable {

		private long pid;
		private Set<Long> processesToMonitor;
		private long lastTime = 0;
		private long lastUpdatedPidList = 0;
		private Sigar sigar;
		private MemoryMessage lastMemoryMessage;
		private CpuMessage lastCpuMessage;

		public MonitorTimerTask(long pid) {
			this.pid = pid;
			sigar = ProcessTools.getSigar();

			if (!isUberAgent) {
				processesToMonitor = ProcessTools.findProcessesToMonitor(pid);
				lastTime = System.currentTimeMillis();
			} else {
				// as uber agent only monitor main process
				processesToMonitor = new HashSet<>();
				processesToMonitor.add(pid);
			}
		}

		@Override
		public void run() {
			if (monitorTasks.contains(MonitorTask.Cpu)) {
				monitorCpu();
			}
			if (monitorTasks.contains(MonitorTask.Memory)) {
				monitorMemory();
			}

			if (!isUberAgent) {
				// check if the child process list should be updated
				long currentTime = System.currentTimeMillis();
				lastUpdatedPidList += (currentTime - lastTime);
				if (lastUpdatedPidList > settings.processChildrenUpdateInterval) {
					// update child process list
					processesToMonitor = ProcessTools.findProcessesToMonitor(pid);
					lastUpdatedPidList = 0;
				}
				lastTime = currentTime;
			}
		}

		private void monitorMemory() {

			long sumVirtualMemory = 0;
			long sumResidentMemory = 0;
			long sumSharedMemory = 0;

			linuxProcessCacheWorkaround();

			for (Long pidToMonitor : processesToMonitor) {
				try {
					ProcMem procMem = sigar.getProcMem(pidToMonitor);
					sumVirtualMemory += procMem.getSize();
					sumResidentMemory += procMem.getResident();
					sumSharedMemory += procMem.getShare();
				} catch (SigarException e) {
					logger.error("Error retrieving memory info from application");
					stop();
				}
			}

			double memUsage = ((double) sumResidentMemory / memTotal) * 100;

			MemoryMessage memoryMessage = new MemoryMessage(null, new Date(), processRunner.getProcessName(),
					sumVirtualMemory, sumResidentMemory, sumSharedMemory, memUsage);

			if (lastMemoryMessage == null || !memoryMessage.equals(lastMemoryMessage)) {
				// only if memory is different to last measuement, send it.
				collectedMetrics.offer(memoryMessage);
			}

			if (settings.logMetrics) {
				if (addCsvHeaderMem) {
					memoryLogFile.println(memoryMessage.getCsvHeader());
					addCsvHeaderMem = false;
				}
				memoryLogFile.println(memoryMessage.toCsvEntry());
				memoryLogFile.flush();
			}

			lastMemoryMessage = memoryMessage;
		}

		private void monitorCpu() {

			double sumCpuUsagePerc = 0;
			long sumCpuTotal = 0;
			long sumCpuUser = 0;
			long sumCpuKernel = 0;

			linuxProcessCacheWorkaround();

			for (Long pidToMonitor : processesToMonitor) {
				try {
					ProcCpu procCpu = sigar.getProcCpu(pidToMonitor);
					sumCpuUsagePerc += procCpu.getPercent() * 100 / cpuCount;
					sumCpuTotal += procCpu.getTotal();
					sumCpuUser += procCpu.getUser();
					sumCpuKernel += procCpu.getSys();
				} catch (SigarException e) {
					logger.error("Error retrieving CPU info from application");
					stop();
				}
			}

			CpuMessage cpuMessage = new CpuMessage(null, new Date(), processRunner.getProcessName(), sumCpuUsagePerc,
					sumCpuTotal, sumCpuKernel, sumCpuUser);

			if (lastCpuMessage == null || !cpuMessage.equals(lastCpuMessage)) {
				// only if memory is different to last measuement, send it.
				collectedMetrics.offer(cpuMessage);
			}

			if (settings.logMetrics) {
				if (addCsvHeaderCpu) {
					cpuLogFile.println(cpuMessage.getCsvHeader());
					addCsvHeaderCpu = false;
				}
				cpuLogFile.println(cpuMessage.toCsvEntry());
				cpuLogFile.flush();
			}

			lastCpuMessage = cpuMessage;
		}

		/**
		 * Sigar caches the last read process for 2 seconds under Linux; If we want a
		 * higher rate of measuring than 2 seconds, we have to request any other
		 * process, so a new reading is done
		 */
		private void linuxProcessCacheWorkaround() {
			if (settings.systemMetricsMonitorInterval < 2000 & ProcessTools.isLinux()
					&& processesToMonitor.size() == 1) {
				try {
					// read init process (pid 1) - should always be available
					sigar.getProcCpu(1);
				} catch (SigarException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class TopMonitor implements Runnable {
		private boolean logTopRunning = false;
		private long pid;

		public TopMonitor(long pid) {
			if (pid < 1) {
				throw new InvalidParameterException();
			}
			this.pid = pid;
		}

		@Override
		public void run() {
			logTopRunning = true;

			int interval = (int) TimeUnit.MILLISECONDS.toSeconds(settings.systemMetricsMonitorInterval);

			ProcessBuilder builder = new ProcessBuilder(
					Arrays.asList("top", "-b", "-d", String.valueOf(interval), "-p", String.valueOf(pid)));
			builder.redirectErrorStream(true);

			try {
				Process process = builder.start();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while (logTopRunning && (line = bufferedReader.readLine().trim()) != null) {
					if (!line.startsWith(String.valueOf(pid))) {
						continue;
					}

					String[] lineSplit = line.split("\\s+");
					double cpuUsage = Double.NaN;
					double memoryUsage = Double.NaN;
					try {
						cpuUsage = Double.parseDouble(lineSplit[8].replaceAll(",", "."));
						memoryUsage = Double.parseDouble(lineSplit[9].replaceAll(",", "."));
					} catch (NumberFormatException e) {
						logger.error("Error parsing usage from top command. Stopping top logging.");
						logTopRunning = false;
					}

					topLogFile.println(new StringJoiner(";").add(String.valueOf(new Date().getTime()))
							.add(String.valueOf(cpuUsage / cpuCount)).add(String.valueOf(memoryUsage)).toString());
					topLogFile.flush();
				}
			} catch (IOException e) {
				logger.error("Error retrieving usage from top command. Stopping top logging.");
			} finally {
				logTopRunning = false;
			}
		}

		public void stop() {
			logTopRunning = false;
		}
	}
}