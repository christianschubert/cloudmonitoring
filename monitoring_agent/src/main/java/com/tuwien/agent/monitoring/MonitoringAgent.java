package com.tuwien.agent.monitoring;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private Sigar sigar;

	public MonitoringAgent() {
		if (initSigar()) {
			startMonitoring();
		}
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

		return true;
	}

	private void startMonitoring() {
		ProcessRunner processRunner = new ProcessRunner("java", "-jar", Constants.RESIZER_PATH);
		long pid = processRunner.start();

		while (true) {

			Set<Long> processesToMonitor = findProcessesToMonitor(pid);
			System.out.println(processesToMonitor);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// processRunner.stop();
	}

	/**
	 * Recursively find out all processes that belong to a given process.<br>
	 * This is done by querying all processes and looking up their parent
	 * process ID.
	 * 
	 * @param pid
	 *            process id of a process
	 * @return a list of all child processes belonging to the given process
	 */
	private Set<Long> findProcessesToMonitor(long pid) {

		HashSet<Long> processesToMonitor = new HashSet<Long>();
		processesToMonitor.add(pid);

		long[] pids;
		try {
			pids = sigar.getProcList();
		} catch (SigarException e) {
			logger.error("Cannot retrieve list of processes");
			return processesToMonitor;
		}

		for (int i = 0; i < pids.length; i++) {
			if (isChildProcessOf(pids[i], pid)) {
				processesToMonitor.add(pids[i]);
			}
		}

		return processesToMonitor;
	}

	private boolean isChildProcessOf(long pid, long parentId) {
		try {
			ProcState state = sigar.getProcState(pid);
			if (state.getPpid() == 0) {
				// no parent process anymore or parent is scheduler
				return false;
			}
			if (state.getPpid() == parentId) {
				return true;
			} else {
				return isChildProcessOf(state.getPpid(), parentId);
			}

		} catch (SigarException e) {
			// Cannot retrieve state of process with pid -> Ignore process,
			// because it is not running anymore
		}
		return false;
	}

	public static void main(String[] args) {
		new MonitoringAgent();
	}
}