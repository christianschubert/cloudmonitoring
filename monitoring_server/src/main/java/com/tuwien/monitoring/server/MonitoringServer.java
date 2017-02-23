package com.tuwien.monitoring.server;

import java.io.IOException;

import org.apache.log4j.Logger;

public class MonitoringServer {

	private final static Logger logger = Logger.getLogger(MonitoringServer.class);

	private static String jmsBrokerURL;

	private JmsService jmsService;

	public MonitoringServer() {
		jmsService = new JmsService(jmsBrokerURL);
		jmsService.start();

		if (!jmsService.isConnected()) {
			logger.error("Error creating JMS service.");
			return;
		}

		logger.info("Monitoring server running. Press any key to exit.");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Monitoring server shutdown.");
		jmsService.stop();
	}

	public static void main(String[] args) {
		if (args.length > 1) {
			logger.error("Invalid number of arguments.");
			return;
		} else if (args.length == 1) {
			jmsBrokerURL = args[0];
		}

		new MonitoringServer();
	}
}