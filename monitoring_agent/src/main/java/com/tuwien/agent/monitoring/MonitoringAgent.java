package com.tuwien.agent.monitoring;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;

public class MonitoringAgent {

	private final static Logger logger = Logger.getLogger(MonitoringAgent.class);

	private String RESIZER_PATH = "../imageresizer/target/imageresizer-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
	private Sigar sigar;

	public MonitoringAgent() {
		System.setProperty("java.library.path", "sigarnatives");
		sigar = new Sigar();

		ProcessRunner processRunner = new ProcessRunner("java", "-jar", RESIZER_PATH);
		long pid = processRunner.start();

		System.out.println(pid);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processRunner.stop();

		// while (true) {
		//
		// try {
		// ProcCpu cpu = sigar.getProcCpu(pid);
		// logger.debug(cpu);
		// } catch (SigarException ex) {
		// ex.printStackTrace();
		// }
		//
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	public static void main(String[] args) {
		new MonitoringAgent();
	}
}