package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MetricAggregationMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MetricMessage> messageList = new ArrayList<MetricMessage>();

	public MetricAggregationMessage() {
	}

	public List<MetricMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<MetricMessage> messageList) {
		this.messageList = messageList;
	}

	public void addMetricMessage(MetricMessage metricMessage) {
		getMessageList().add(metricMessage);
	}
}