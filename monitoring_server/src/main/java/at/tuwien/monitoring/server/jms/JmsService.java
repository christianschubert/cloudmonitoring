package at.tuwien.monitoring.server.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import at.tuwien.common.GlobalConstants;
import at.tuwien.monitoring.jms.messages.MetricAggregationMessage;

public class JmsService implements MessageListener {

	private String brokerURL = ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;

	private BrokerService broker;
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;

	private MessageConsumer consumerAgents;
	private MessageConsumer consumerClients;

	private boolean embeddedJmsBroker;
	private boolean connected = false;

	public JmsService(final boolean embeddedJmsBroker) {
		// use default broker URL
		this(null, embeddedJmsBroker);
	}

	public JmsService(final String brokerURL, final boolean embeddedJmsBroker) {
		// use default broker URL if null
		if (brokerURL != null) {
			this.brokerURL = brokerURL;
		}

		this.embeddedJmsBroker = embeddedJmsBroker;
	}

	@Override
	public void onMessage(final Message message) {
		try {

			String senderIP = message.getStringProperty(GlobalConstants.IP_ADDRESS_PROPERTY);

			if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received: " + text);

			}
			else if (message instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				Serializable serializable = objectMessage.getObject();

				if (serializable instanceof MetricAggregationMessage) {
					MetricAggregationMessage metricAggregationMessage = (MetricAggregationMessage) serializable;
					System.out.println(metricAggregationMessage);
				}
			}
		}
		catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			if (embeddedJmsBroker) {
				// Start a new broker
				broker = new BrokerService();

				// Configure the broker
				broker.addConnector(brokerURL);
				broker.setPersistent(false);
				broker.start();
			}

			// Create a ConnectionFactory
			connectionFactory = new ActiveMQConnectionFactory(brokerURL);
			connectionFactory.setTrustAllPackages(true);
			// connectionFactory.setTrustedPackages(Arrays.asList("at.tuwien.monitoring.jms.messages"));

			// Create a Connection
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destinationAgents = session.createQueue(GlobalConstants.QUEUE_AGENTS);
			Destination destinationClients = session.createQueue(GlobalConstants.QUEUE_CLIENTS);

			// Create a MessageConsumer from the Session to the Topic or Queue
			consumerAgents = session.createConsumer(destinationAgents);
			consumerClients = session.createConsumer(destinationClients);

			consumerAgents.setMessageListener(this);
			consumerClients.setMessageListener(this);

			connected = true;

		}
		catch (JMSException e) {
			e.printStackTrace();
			stop();
		}
		catch (Exception e) {
			e.printStackTrace();
			stop();
		}
	}

	public void stop() {
		if (consumerAgents != null) {
			try {
				consumerAgents.close();
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (consumerClients != null) {
			try {
				consumerClients.close();
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (session != null) {
			try {
				session.close();
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			}
			catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (broker != null && !broker.isStopped()) {
			try {
				broker.stop();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		connected = false;
	}

	public boolean isConnected() {
		return connected;
	}
}
