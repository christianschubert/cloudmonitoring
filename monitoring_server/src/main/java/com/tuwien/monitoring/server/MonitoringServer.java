package com.tuwien.monitoring.server;

public class MonitoringServer {

	public MonitoringServer() {
		JmsService jmsService = new JmsService();
		jmsService.start();
		System.out.println(jmsService.isConnected());
		jmsService.stop();
	}

	public static void main(String[] args) {

		new MonitoringServer();
	}
}