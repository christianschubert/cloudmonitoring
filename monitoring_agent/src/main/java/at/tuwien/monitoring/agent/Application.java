package at.tuwien.monitoring.agent;

import java.util.Set;

import at.tuwien.monitoring.agent.constants.MonitorTask;

public class Application {
	private String applicationPath;
	private String params;
	private Set<MonitorTask> monitorTasks;

	public Application(String applicationPath, Set<MonitorTask> monitorTasks) {
		this(applicationPath, "", monitorTasks);
	}

	public Application(String applicationPath, String params, Set<MonitorTask> monitorTasks) {
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

	public Set<MonitorTask> getMonitorTasks() {
		return monitorTasks;
	}

	public void setMonitorTasks(Set<MonitorTask> monitorTasks) {
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
		return "Application [applicationPath=" + applicationPath + ", params=" + params + ", monitorTasks=" + monitorTasks
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationPath == null) ? 0 : applicationPath.hashCode());
		result = prime * result + ((monitorTasks == null) ? 0 : monitorTasks.hashCode());
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		if (applicationPath == null) {
			if (other.applicationPath != null)
				return false;
		} else if (!applicationPath.equals(other.applicationPath))
			return false;
		if (monitorTasks == null) {
			if (other.monitorTasks != null)
				return false;
		} else if (!monitorTasks.equals(other.monitorTasks))
			return false;
		if (params == null) {
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}
}