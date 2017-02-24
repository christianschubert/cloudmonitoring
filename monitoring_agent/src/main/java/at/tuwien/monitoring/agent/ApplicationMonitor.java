package at.tuwien.monitoring.agent;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.process.ProcessRunner;
import at.tuwien.monitoring.agent.process.ProcessTools;
import at.tuwien.monitoring.jms.messages.CpuLoadMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class ApplicationMonitor {

	private Sigar sigar;
	private int cpuCount;

	private ProcessRunner processRunner;

	private ScheduledFuture<?> scheduledFuture;

	private List<MonitorTask> monitorTasks;
	private Queue<MetricMessage> collectedMetrics = new ConcurrentLinkedQueue<MetricMessage>();

	private ScheduledExecutorService scheduler;

	public ApplicationMonitor(Sigar sigar, int cpuCount, String[] applicationWithParams,
			List<MonitorTask> monitorTasks) {
		this.sigar = sigar;
		this.cpuCount = cpuCount;
		this.monitorTasks = monitorTasks;
		processRunner = new ProcessRunner(applicationWithParams);
		scheduler = Executors.newScheduledThreadPool(1);
	}

	public void start() {
		long pid = processRunner.start();
		if (pid == -1) {
			// Error starting process and retreiving pid
			stop();
			return;
		}

		scheduledFuture = scheduler.scheduleAtFixedRate(new MonitorTimerTask(pid),
				Constants.PROCESS_MONITOR_START_DELAY, Constants.PROCESS_MONITOR_INTERVAL, TimeUnit.MILLISECONDS);
	}

	public void stop() {
		if (scheduledFuture != null) {
			scheduledFuture.cancel(true);
		}
		if (scheduler != null) {
			scheduler.shutdown();
			try {
				scheduler.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (processRunner != null) {
			processRunner.stop();
		}
	}

	public Queue<MetricMessage> getCollectedMetrics() {
		return collectedMetrics;
	}

	public class MonitorTimerTask implements Runnable {

		private long pid;
		private Set<Long> processesToMonitor;

		public MonitorTimerTask(long pid) {
			this.pid = pid;
			processesToMonitor = ProcessTools.findProcessesToMonitor(pid);
		}

		@Override
		public void run() {
			if (monitorTasks.contains(MonitorTask.CpuLoad)) {
				monitorCpuLoad();
			} else if (monitorTasks.contains(MonitorTask.Memory)) {
				// TODO
			}
		}

		private void monitorCpuLoad() {

			double sumCpuLoad = 0.0d;

			for (Long pidToMonitor : processesToMonitor) {
				try {
					sumCpuLoad += sigar.getProcCpu(pidToMonitor).getPercent() * 100 / cpuCount;
				} catch (SigarException e) {
					e.printStackTrace();
				}
			}

			collectedMetrics
					.offer(new CpuLoadMessage(processRunner.getProcessName(), new Date().getTime(), sumCpuLoad));
		}
	}
}