package at.tuwien.monitoring.server.processing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.WrapperEventBean;

import at.tuwien.common.Settings;
import at.tuwien.monitoring.jms.messages.ClientInfoMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;
import at.tuwien.monitoring.server.db.dao.ViolationDAO;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;

public class MetricEventListener implements StatementAwareUpdateListener {

	private final static Logger logger = Logger.getLogger(MetricEventListener.class);

	private Settings settings;
	private PrintWriter outLogFileViolations;
	private PrintWriter outLogFileSuccessability;
	private PrintWriter outLogFileThroughput;
	private boolean writeHeader = true;

	private ViolationDAO violationDAO = new ViolationDAO();

	public MetricEventListener(Settings settings) {
		this.settings = settings;

		if (settings.logMetrics) {
			try {
				FileWriter fwViolations = new FileWriter(settings.etcFolderPath + "/logs/logs_ttp_violations.csv");
				BufferedWriter bwViolations = new BufferedWriter(fwViolations);
				outLogFileViolations = new PrintWriter(bwViolations);

				FileWriter fwSuccessability = new FileWriter(
						settings.etcFolderPath + "/logs/logs_ttp_successability.csv");
				BufferedWriter bwSuccessability = new BufferedWriter(fwSuccessability);
				outLogFileSuccessability = new PrintWriter(bwSuccessability);
				outLogFileSuccessability.println("sourceMessageId;monitoredValue");

				FileWriter fwThroughput = new FileWriter(settings.etcFolderPath + "/logs/logs_ttp_throughput.csv");
				BufferedWriter bwThroughput = new BufferedWriter(fwThroughput);
				outLogFileThroughput = new PrintWriter(bwThroughput);
				outLogFileThroughput.println("timestamp;monitoredValue");

			} catch (IOException e) {
				settings.logMetrics = false;
				logger.error("Error logging metrics to file.");
			}
		}
	}

	public void stop() {
		if (outLogFileViolations != null) {
			outLogFileViolations.close();
		}

		if (outLogFileSuccessability != null) {
			outLogFileSuccessability.close();
		}

		if (outLogFileThroughput != null) {
			outLogFileThroughput.close();
		}
	}

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement,
			EPServiceProvider epServiceProvider) {

		if (newEvents == null) {
			return;
		}

		EventBean event = newEvents[0];
		MetricMessage metricMessage = null;
		Map<String, Object> properties = null;

		if (event.getUnderlying() instanceof Pair<?, ?>) {
			Pair<?, ?> pair = (Pair<?, ?>) event.getUnderlying();
			metricMessage = (MetricMessage) pair.getFirst();
			properties = ((WrapperEventBean) event).getDecoratingProperties();
		} else {
			logger.error("Unknown EventBean.");
			return;
		}

		Object logging = properties.get("logging");
		if (logging != null) {
			logSnapshot((String) logging, properties, metricMessage);
			return;
		}

		logger.info("New violation: " + properties);

		ViolationDTO violationDTO = new ViolationDTO();
		violationDTO.setRequiredDesc((String) properties.get("requirementdesc"));
		violationDTO.setViolationType((String) properties.get("metrictype"));
		violationDTO.setSourceIpAddress(metricMessage.getIpAddress());
		violationDTO.setViolationTimestamp(metricMessage.getTimestamp());
		violationDTO.setServiceName(metricMessage.getServiceName());

		Object monitoredValue = properties.get("monitoredvalue");
		if (monitoredValue instanceof Long) {
			Long longValue = (Long) monitoredValue;
			violationDTO.setMonitoredValue(longValue.doubleValue());
		} else if (monitoredValue instanceof Integer) {
			Integer intValue = (Integer) monitoredValue;
			violationDTO.setMonitoredValue(intValue.doubleValue());
		} else if (monitoredValue instanceof Double) {
			Double doubleValue = (Double) monitoredValue;
			violationDTO.setMonitoredValue(doubleValue.doubleValue());
		} else {
			violationDTO.setMonitoredValue((double) monitoredValue);
		}

		violationDAO.insert(violationDTO);

		if (settings.logMetrics) {
			if (writeHeader) {
				outLogFileViolations.println(violationDTO.getCsvHeader());
				writeHeader = false;
			}

			if (metricMessage instanceof ClientInfoMessage) {
				violationDTO.setSourceMessageId(((ClientInfoMessage) metricMessage).getId());
			}

			outLogFileViolations.println(violationDTO.toCsvEntry());
			outLogFileViolations.flush();
		}
	}

	private void logSnapshot(String type, Map<String, Object> properties, MetricMessage metricMessage) {

		Object monitoredValue = properties.get("monitoredvalue");

		if (type.equalsIgnoreCase("successability")) {
			int id = -1;
			if (metricMessage instanceof ClientInfoMessage) {
				id = ((ClientInfoMessage) metricMessage).getId();
			}

			outLogFileSuccessability.println(id + ";" + monitoredValue);
			outLogFileSuccessability.flush();

		} else if (type.equalsIgnoreCase("throughput")) {
			outLogFileThroughput.println(new Date().getTime() + ";" + monitoredValue);
			outLogFileThroughput.flush();
		}
	}
}