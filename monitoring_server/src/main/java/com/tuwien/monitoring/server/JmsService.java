package com.tuwien.monitoring.server;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsService implements MessageListener {

	private final static String QUEUE_NAME = "TESTQUEUE";

	private String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL;

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;

	private boolean connected = false;

	public JmsService() {
		// use default broker URL
	}

	public JmsService(String brokerURL) {
		this.brokerURL = brokerURL;
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				String text = textMessage.getText();
				System.out.println("Received: " + text);
			} catch (JMSException e) {
				e.printStackTrace();
			}
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
			Destination destination = session.createQueue(QUEUE_NAME);

			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(destination);

			consumer.setMessageListener(this);

			connected = true;

		} catch (JMSException e) {
			e.printStackTrace();
			stop();
		}
	}

	public void stop() {
		if (consumer != null) {
			try {
				consumer.close();
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
