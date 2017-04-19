package at.tuwien.monitoring.server.processing;

import org.apache.log4j.Logger;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import at.tuwien.monitoring.jms.messages.MetricMessage;

public class MetricProcessor {

	private final static Logger logger = Logger.getLogger(MetricProcessor.class);

	private EPServiceProvider epService;
	private MetricEventListener metricEventListener;

	public boolean start() {
		Configuration config = new Configuration();
		config.addEventTypeAutoName("at.tuwien.monitoring.jms.messages");
		epService = EPServiceProviderManager.getDefaultProvider(config);
		metricEventListener = new MetricEventListener();
		return true;
	}

	public String addExpression(String expression) {
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		statement.addListener(metricEventListener);

		logger.info("Added new statement: " + statement.getName());

		return statement.getName();
	}

	public void stopStatement(String statementName) {
		epService.getEPAdministrator().getStatement(statementName).stop();

		logger.info("Stopped statement: " + statementName);
	}

	public void addEvent(MetricMessage metricMessage) {
		logger.info("New Event: " + metricMessage);
		epService.getEPRuntime().sendEvent(metricMessage);
	}

	public void stop() {
		if (epService != null) {
			epService.destroy();
		}
	}
}