package com.tuwien.agent.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

public class Main {

	private final static Logger logger = Logger.getLogger(Main.class);

	private String appPath = "C:\\Users\\Christian\\Desktop\\imageresizer.jar";

	private Sigar sigar = new Sigar();

	private void runProcess() {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", appPath);
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();
			InputStream inputStream = process.getInputStream();

			new Thread(new ProcessReader(inputStream)).start();

			long pid = getProcessId(process);
			if (pid == -1) {
				logger.error("Error retrieving PID");
				return;
			}

			logger.debug("PID of child process is " + pid);

			while (true) {

				try {
					ProcCpu cpu = sigar.getProcCpu(pid);
					logger.debug(cpu);
				} catch (SigarException ex) {
					ex.printStackTrace();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// inputStream.close();
			// process.destroy();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class ProcessReader implements Runnable {

		private BufferedReader reader;

		public ProcessReader(InputStream inputStream) {
			reader = new BufferedReader(new InputStreamReader(inputStream));
		}

		@Override
		public void run() {
			if (reader == null) {
				logger.error("Cannot read from child process. BufferedReader is null");
				return;
			}

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					logger.info(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private long getProcessId(Process process) {
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

	public static void main(String[] args) {
		new Main().runProcess();
	}
}