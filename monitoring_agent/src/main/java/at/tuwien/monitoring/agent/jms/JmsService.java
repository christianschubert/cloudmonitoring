package at.tuwien.monitoring.agent.jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import at.tuwien.monitoring.agent.constants.Constants;

public class JmsService {

	private String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	private boolean connected = false;

	public JmsService() {
		// use default broker URL
	}

	public JmsService(String brokerURL) {
		// use default broker URL if null
		if (brokerURL != null) {
			this.brokerURL = brokerURL;
		}
	}

	public void start() {
		try {
			// Create a ConnectionFactory
			connectionFactory = new ActiveMQConnectionFactory(brokerURL);

			// Create a Connection
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue(Constants.QUEUE_AGENTS);

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			connected = true;

		} catch (JMSException e) {
			e.printStackTrace();
			stop();
		}
	}

	public boolean sendTextMessage(String message) {
		if (!connected) {
			return false;
		}
		try {
			// Create a messages
			TextMessage textMessage = session.createTextMessage(message);
			// Tell the producer to send the message
			producer.send(textMessage);
			return true;

		} catch (JMSException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void stop() {
		if (producer != null) {
			try {
				producer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		connected = false;
	}

	public boolean isConnected() {
		return connected;
	}
}
