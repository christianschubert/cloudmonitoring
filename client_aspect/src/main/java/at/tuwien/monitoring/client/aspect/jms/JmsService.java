package at.tuwien.monitoring.client.aspect.jms;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import at.tuwien.monitoring.client.constants.Constants;

public class JmsService {

	private final static Logger logger = Logger.getLogger(JmsService.class);

	private String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private String publicIPAddress;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	private boolean connected = false;

	public JmsService() {
		// use default broker URL
		// do not append public IP address with the messages
		this(null);
	}

	public JmsService(String publicIPAddress) {
		// use default broker URL
		this(null, publicIPAddress);
	}

	public JmsService(String brokerURL, String publicIPAddress) {
		// use default broker URL if null
		if (brokerURL != null) {
			this.brokerURL = brokerURL;
		}
		this.publicIPAddress = publicIPAddress;
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
			logger.error("Error connecting to JMS broker.");
			e.printStackTrace();
			stop();
		}
	}

	public boolean sendObjectMessage(Serializable object) {
		if (!connected || object == null) {
			logger.error("Cannot send message. Not connected or message is null.");
			return false;
		}
		try {
			// Create a messages
			ObjectMessage objectMessage = session.createObjectMessage(object);

			// append IP address of sender as a property
			if (publicIPAddress != null) {
				objectMessage.setStringProperty(Constants.IP_ADDRESS_PROPERTY, publicIPAddress);
			}

			// Tell the producer to send the message
			producer.send(objectMessage);
			return true;

		} catch (JMSException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean sendTextMessage(String message) {
		if (!connected || message == null) {
			logger.error("Cannot send message. Not connected or message is null.");
			return false;
		}
		try {
			// Create a message
			TextMessage textMessage = session.createTextMessage(message);

			// append IP address of sender as a property
			if (publicIPAddress != null) {
				textMessage.setStringProperty(Constants.IP_ADDRESS_PROPERTY, publicIPAddress);
			}

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
