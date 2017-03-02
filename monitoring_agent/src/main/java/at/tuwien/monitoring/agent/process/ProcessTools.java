package at.tuwien.monitoring.agent.process;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

public class ProcessTools {

	private final static Logger logger = Logger.getLogger(ProcessTools.class);

	private static Sigar sigar;

	public static void setSigar(Sigar sigar) {
		ProcessTools.sigar = sigar;
	}

	public static long getProcessIdFromProcess(Process process) {
		long pid = -1;

		if (process.getClass().getName().equals("java.lang.Win32Process")
				|| process.getClass().getName().equals("java.lang.ProcessImpl")) {

			// Windows

			try {
				Field f = process.getClass().getDeclaredField("handle");
				f.setAccessible(true);
				long handl = f.getLong(process);
				f.setAccessible(false);

				Kernel32 kernel = Kernel32.INSTANCE;
				WinNT.HANDLE handle = new WinNT.HANDLE();
				handle.setPointer(Pointer.createConstant(handl));

				pid = kernel.GetProcessId(handle);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}

		} else if (process.getClass().getName().equals("java.lang.UNIXProcess")) {

			// MacOS or Linux

			try {
				Field f = process.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				pid = f.getLong(process);
				f.setAccessible(false);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return pid;
	}

	/**
	 * Recursively find out all processes that belong to a given process.<br>
	 * This is done by querying all processes and looking up their parent
	 * process ID.
	 * 
	 * @param pid
	 *            process id of a process
	 * @return a list of all child processes belonging to the given process
	 *         including the process itself
	 */
	public static Set<Long> findProcessesToMonitor(long pid) {

		Set<Long> processesToMonitor = new HashSet<>();
		Set<Long> evaluatedProcesses = new HashSet<>();
		processesToMonitor.add(pid);
		evaluatedProcesses.add(pid);

		long[] pids;
		try {
			pids = sigar.getProcList();
		} catch (SigarException e) {
			logger.error("Cannot retrieve list of processes");
			return processesToMonitor;
		}

		for (int i = 0; i < pids.length; i++) {
			if (isChildProcessOf(pids[i], pid, evaluatedProcesses, processesToMonitor)) {
				processesToMonitor.add(pids[i]);
			}
		}

		return processesToMonitor;
	}

	private static boolean isChildProcessOf(long pid, long parentId, Set<Long> evaluatedProcesses,
			Set<Long> processesToMonitor) {
		if (evaluatedProcesses.contains(pid)) {
			// already checked -> return result
			return processesToMonitor.contains(pid);
		}
		evaluatedProcesses.add(pid);

		try {
			ProcState state = sigar.getProcState(pid);
			if (state.getPpid() == 0) {
				// no parent process anymore or parent is scheduler
				return false;
			}
			if (state.getPpid() == parentId) {
				return true;
			} else {
				return isChildProcessOf(state.getPpid(), parentId, evaluatedProcesses, processesToMonitor);
			}

		} catch (SigarException e) {
			// Cannot retrieve state of process with pid -> Ignore process,
			// because it is not running anymore
		}
		return false;
	}
}