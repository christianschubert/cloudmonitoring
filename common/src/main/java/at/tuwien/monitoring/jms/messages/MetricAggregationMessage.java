package at.tuwien.monitoring.jms.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricAggregationMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<MetricMessage> messageList = new ArrayList<MetricMessage>();

	public MetricAggregationMessage() {
	}

	public MetricAggregationMessage(MetricMessage... messages) {
		messageList = new ArrayList<>(Arrays.asList(messages));
	}

	public List<MetricMessage> getMessageList() {
		return messageList;
	}

	public void addMetricMessage(MetricMessage metricMessage) {
		messageList.add(metricMessage);
	}

	@Override
	public String toString() {
		return "MetricAggregationMessage [messageList=" + messageList + "]";
	}
}