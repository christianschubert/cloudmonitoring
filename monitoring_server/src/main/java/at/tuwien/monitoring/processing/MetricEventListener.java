package at.tuwien.monitoring.processing;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;

public class MetricEventListener implements StatementAwareUpdateListener {

	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement,
			EPServiceProvider epServiceProvider) {
		System.out.println(statement.getText());
		EventBean event = newEvents[0];
		System.out.println(event.getUnderlying());
	}
}