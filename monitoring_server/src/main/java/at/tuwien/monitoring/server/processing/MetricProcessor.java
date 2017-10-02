package at.tuwien.monitoring.server.processing;

import org.apache.log4j.Logger;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import at.tuwien.common.Settings;
import at.tuwien.monitoring.jms.messages.MetricMessage;

public class MetricProcessor {

	private final static Logger logger = Logger.getLogger(MetricProcessor.class);

	private Settings settings;

	private EPServiceProvider epService;
	private MetricEventListener metricEventListener;

	public MetricProcessor(Settings settings) {
		this.settings = settings;
	}

	public boolean start() {
		Configuration config = new Configuration();
		config.addEventTypeAutoName("at.tuwien.monitoring.jms.messages");
		epService = EPServiceProviderManager.getDefaultProvider(config);
		metricEventListener = new MetricEventListener(settings);
		return true;
	}

	public String addExpression(String expression) {
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		statement.addListener(metricEventListener);

		logger.info("New statement: " + statement.getName() + "; " + expression);

		return statement.getName();
	}

	public void stopStatement(String statementName) {
		epService.getEPAdministrator().getStatement(statementName).stop();

		logger.info("Stopped statement: " + statementName);
	}

	public void addEvent(MetricMessage metricMessage) {
		logger.trace("New Event: " + metricMessage);
		epService.getEPRuntime().sendEvent(metricMessage);
	}

	public void stop() {
		if (metricEventListener != null) {
			metricEventListener.stop();
		}
		if (epService != null) {
			epService.destroy();
		}
	}
}