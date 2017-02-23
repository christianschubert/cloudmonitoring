package at.tuwien.monitoring.agent;

import java.util.List;

import org.hyperic.sigar.Sigar;

import at.tuwien.monitoring.agent.constants.MonitorTask;
import at.tuwien.monitoring.agent.process.ProcessRunner;

public class ApplicationMonitor {

	private Sigar sigar;
	private ProcessRunner processRunner;

	public ApplicationMonitor(Sigar sigar, String[] applicationWithParams, List<MonitorTask> monitorTasks) {
		this.sigar = sigar;
		processRunner = new ProcessRunner(applicationWithParams);
	}

	public void start() {
		long pid = processRunner.start();
		if (pid == -1) {
			// Error starting process and retreiving pid
			return;
		}

		// while (true) {
		//
		// Set<Long> processesToMonitor =
		// ProcessTools.findProcessesToMonitor(pid);
		// // System.out.println(processesToMonitor);
		//
		// try {
		// jmsService.sendObjectMessage(
		// new CpuLoadMessage("java", new Date().getTime(),
		// sigar.getProcCpu(pid).getPercent()));
		// } catch (SigarException e1) {
		// e1.printStackTrace();
		// }
		//
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
	}
}