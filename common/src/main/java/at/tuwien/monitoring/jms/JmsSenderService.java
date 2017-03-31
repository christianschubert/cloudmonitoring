package at.tuwien.monitoring.jms;

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

import at.tuwien.common.GlobalConstants;

public class JmsSenderService {

	private final static Logger logger = Logger.getLogger(JmsSenderService.class);

	private String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private String publicIPAddress;
	private String queue;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	private boolean connected = false;

	public JmsSenderService(String queue) {
		// use default broker URL
		// do not append public IP address with the messages
		this(null, queue);
	}

	public JmsSenderService(String publicIPAddress, String queue) {
		// use default broker URL
		this(null, publicIPAddress, queue);
	}

	public JmsSenderService(String brokerURL, String publicIPAddress, String queue) {
		// use default broker URL if null
		if (brokerURL != null) {
			this.brokerURL = brokerURL;
		}
		this.publicIPAddress = publicIPAddress;
		this.queue = queue;
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
			Destination destination = session.createQueue(queue);

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			connected = true;

		} catch (JMSException e) {
			e.printStackTrace();
			stop();
		}
	}

	public boolean sendObjectMessage(Serializable object) {
		if (!connected || object == null) {
			logger.error("Cannot send message. Not connected or message is null");
			return false;
		}
		try {
			// Create a messages
			ObjectMessage objectMessage = session.createObjectMessage(object);

			// append IP address of sender as a property
			if (publicIPAddress != null) {
				objectMessage.setStringProperty(GlobalConstants.IP_ADDRESS_PROPERTY, publicIPAddress);
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
			logger.error("Cannot send message. Not connected or message is null");
			return false;
		}
		try {
			// Create a message
			TextMessage textMessage = session.createTextMessage(message);

			// append IP address of sender as a property
			if (publicIPAddress != null) {
				textMessage.setStringProperty(GlobalConstants.IP_ADDRESS_PROPERTY, publicIPAddress);
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
