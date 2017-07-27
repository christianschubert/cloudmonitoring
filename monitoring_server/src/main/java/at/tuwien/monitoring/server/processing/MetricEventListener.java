package at.tuwien.monitoring.server.processing;

import java.util.Map;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.WrapperEventBean;

import at.tuwien.monitoring.jms.messages.MetricMessage;
import at.tuwien.monitoring.server.db.dao.ViolationDAO;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;
import at.tuwien.monitoring.server.types.ViolationType;

public class MetricEventListener implements StatementAwareUpdateListener {

	private final static Logger logger = Logger.getLogger(MetricEventListener.class);

	private ViolationDAO violationDAO = new ViolationDAO();

	@Override
	public void update(final EventBean[] newEvents, final EventBean[] oldEvents, final EPStatement statement,
			final EPServiceProvider epServiceProvider) {

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

		logger.info("New violation: " + properties);

		ViolationDTO violationDTO = new ViolationDTO();
		violationDTO.setRequiredDesc((String) properties.get("requirementdesc"));
		violationDTO.setViolationType(ViolationType.get((String) properties.get("metrictype")));
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
	}
}