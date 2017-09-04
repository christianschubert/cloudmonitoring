package at.tuwien.monitoring.agent;

import java.util.List;

import at.tuwien.monitoring.agent.constants.MonitorTask;

public class Application {
	private String applicationPath;
	private String params;
	private List<MonitorTask> monitorTasks;

	public Application(String applicationPath, List<MonitorTask> monitorTasks) {
		this(applicationPath, "", monitorTasks);
	}

	public Application(String applicationPath, String params, List<MonitorTask> monitorTasks) {
		super();
		this.applicationPath = applicationPath;
		this.params = params;
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "Application [applicationPath=" + applicationPath + ", params=" + params + ", monitorTasks=" + monitorTasks + "]";
	}
}