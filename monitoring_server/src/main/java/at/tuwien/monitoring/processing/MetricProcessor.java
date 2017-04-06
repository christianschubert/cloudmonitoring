package at.tuwien.monitoring.processing;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import at.tuwien.monitoring.jms.messages.MetricMessage;

public class MetricProcessor {
	EPServiceProvider epService;
	MetricEventListener metricEventListener;

	public boolean start() {
		Configuration config = new Configuration();
		config.addEventTypeAutoName("at.tuwien.monitoring.jms.messages");
		epService = EPServiceProviderManager.getDefaultProvider(config);
		metricEventListener = new MetricEventListener();

		addExpression("select * from ClientResponseTimeMessage having responseTime > 2000");

		return true;
	}

	public void addExpression(String expression) {
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		statement.addListener(metricEventListener);
	}

	public void addEvent(MetricMessage metricMessage) {
		epService.getEPRuntime().sendEvent(metricMessage);
	}

	public void stop() {
		if (epService != null) {
			epService.destroy();
		}
	}
}