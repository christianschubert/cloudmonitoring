package at.tuwien.monitoring.server.processing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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

	private Map<Class<? extends MetricMessage>, PrintWriter> printWriters = new HashMap<>();

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

		if (settings.logMetrics) {
			if (!printWriters.containsKey(metricMessage.getClass())) {
				initWriter(metricMessage);
			}

			PrintWriter writer = printWriters.get(metricMessage.getClass());
			if (writer != null) {
				writer.println(metricMessage.toCsvEntry());
				writer.flush();
			}
		}
	}

	private void initWriter(MetricMessage metricMessage) {
		try {
			FileWriter fw = new FileWriter(settings.etcFolderPath + "/logs/logs_ttp_"
					+ metricMessage.getClass().getSimpleName().toLowerCase() + ".csv");
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);

			writer.println(metricMessage.getCsvHeader());
			writer.flush();

			printWriters.put(metricMessage.getClass(), writer);

		} catch (IOException e) {
			settings.logMetrics = false;
			logger.error("Error logging metrics to file.");
		}
	}

	public void stop() {
		if (metricEventListener != null) {
			metricEventListener.stop();
		}

		for (PrintWriter writer : printWriters.values()) {
			if (writer != null) {
				writer.close();
			}
		}

		if (epService != null) {
			epService.destroy();
		}
	}
}