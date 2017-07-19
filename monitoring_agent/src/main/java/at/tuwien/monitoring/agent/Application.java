package at.tuwien.monitoring.agent;

import java.util.List;

import at.tuwien.monitoring.agent.constants.MonitorTask;

public class Application {
	private String applicationPath;
	private List<MonitorTask> monitorTasks;

	public Application(String applicationPath, List<MonitorTask> monitorTasks) {
		super();
		this.applicationPath = applicationPath;
		this.monitorTasks = monitorTasks;
	}

	public String getApplicationPath() {
		return applicationPath;
	}

	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}

	public List<MonitorTask> getMonitorTasks() {
		return monitorTasks;
	}

	public void setMonitorTasks(List<MonitorTask> monitorTasks) {
		this.monitorTasks = monitorTasks;
	}

	@Override
	public String toString() {
		return "Application [applicationPath=" + applicationPath + ", monitorTasks=" + monitorTasks + "]";
	}
}