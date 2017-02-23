package at.tuwien.monitoring.agent;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import at.tuwien.monitoring.agent.constants.Constants;
import at.tuwien.monitoring.agent.jms.JmsService;
import at.tuwien.monitoring.agent.process.ProcessRunner;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private static String jmsBrokerURL;

	private Sigar sigar;
	private JmsService jmsService;

	public MonitoringAgent() {
		if (!initSigar()) {
			return;
		}

		jmsService = new JmsService(jmsBrokerURL);
		jmsService.start();
		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return;
		}

		startMonitoring();
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
			// System.out.println(processesToMonitor);

			try {
				jmsService.sendTextMessage(String.valueOf(sigar.getProcCpu(pid).getPercent()));
			} catch (SigarException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		if (args.length > 1) {
			logger.error("Invalid number of arguments.");
			return;
		} else if (args.length == 1) {
			jmsBrokerURL = args[0];
		}

		new MonitoringAgent();
	}
}