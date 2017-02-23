package at.tuwien.monitoring.agent.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ProcessRunner {

	private final static Logger logger = Logger.getLogger(ProcessRunner.class);

	private final ExecutorService pool;

	private Process process;

	private String args[];
	private long pid = -1;

	private boolean running = false;

	public ProcessRunner(String... args) {
		this.args = args;
		pool = Executors.newFixedThreadPool(1);
	}

	public long start() {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(args);
			processBuilder.redirectErrorStream(true);
			process = processBuilder.start();

			pid = ProcessTools.getProcessIdFromProcess(process);
			if (pid == -1) {
				logger.error("Error retrieving PID");
				stop();
				return -1;
			}

			running = true;
			logger.info("New Process \"" + getProcessName() + "\" started. PID of process is " + pid);

			pool.submit(new ProcessReader(process.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
			stop();
			return -1;
		}
		return pid;
	}

	public void stop() {

		running = false;

		if (process != null) {
			process.destroy();
		}
		if (pool != null) {
			pool.shutdown();
			try {
				pool.awaitTermination(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public long getPid() {
		return pid;
	}

	public String getProcessName() {
		return args != null ? args[0] : null;
	}

	public String getProcessNameWithArguments() {
		return args != null ? String.join(" ", args) : null;
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
				while (isRunning() && (line = reader.readLine()) != null) {
					logger.info("Process \"" + getProcessName() + "\": " + line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				cleanup();
			}
		}

		private void cleanup() {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
