package at.tuwien.monitoring.server.processing;

import java.util.Map;

import org.apache.log4j.Logger;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.event.WrapperEventBean;

import at.tuwien.monitoring.jms.messages.ClientResponseTimeMessage;
import at.tuwien.monitoring.jms.messages.CpuLoadMessage;
import at.tuwien.monitoring.jms.messages.MemoryMessage;
import at.tuwien.monitoring.jms.messages.MetricMessage;
import at.tuwien.monitoring.server.db.dao.ViolationDAO;
import at.tuwien.monitoring.server.db.dto.ViolationDTO;
import at.tuwien.monitoring.server.types.ViolationType;

public class MetricEventListener implements StatementAwareUpdateListener {

	private final static Logger logger = Logger.getLogger(MetricEventListener.class);

	private Map<String, String> requirementDescriptions;
	private ViolationDAO violationDAO = new ViolationDAO();

	public MetricEventListener(final Map<String, String> requirementDescriptions) {
		this.requirementDescriptions = requirementDescriptions;
	}

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
		}
		else if (event.getUnderlying() instanceof MetricMessage) {
			metricMessage = (MetricMessage) event.getUnderlying();
		}
		else {
			logger.error("Unknown EventBean.");
			return;
		}

		logger.info("New violation: " + metricMessage);

		String requirementDesc = requirementDescriptions.get(statement.getName());

		ViolationDTO violationDTO = new ViolationDTO();
		violationDTO.setRequiredDesc(requirementDesc);
		violationDTO.setSourceIpAddress(metricMessage.getIpAddress());
		violationDTO.setViolationTimestamp(metricMessage.getTimestamp());

		if (metricMessage instanceof ClientResponseTimeMessage) {
			ClientResponseTimeMessage clientResponseTimeMessage = (ClientResponseTimeMessage) metricMessage;
			violationDTO.setViolationType(ViolationType.RESPONSE_TIME);
			violationDTO.setServiceName(clientResponseTimeMessage.getMethod().toString() + " " + clientResponseTimeMessage.getTarget());
			violationDTO.setMonitoredValue(clientResponseTimeMessage.getResponseTime());

		}
		else if (metricMessage instanceof MemoryMessage) {
			MemoryMessage memoryMessage = (MemoryMessage) metricMessage;
			violationDTO.setViolationType(ViolationType.MEM_RESIDENT);
			violationDTO.setServiceName(memoryMessage.getApplication());
			violationDTO.setMonitoredValue(memoryMessage.getResidentMemory());

		}
		else if (metricMessage instanceof CpuLoadMessage) {
			CpuLoadMessage cpuLoadMessage = (CpuLoadMessage) metricMessage;
			violationDTO.setViolationType(ViolationType.CPU_LOAD);
			violationDTO.setServiceName(cpuLoadMessage.getApplication());
			violationDTO.setMonitoredValue(cpuLoadMessage.getCpuLoad());
		}

		System.out.println(violationDTO);

		//		violationDAO.insert(violationDTO);
	}
}