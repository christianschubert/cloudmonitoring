package com.tuwien.monitoring.server;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	public Sender() {

		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue("TESTQUEUE");

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a messages
			TextMessage message = session.createTextMessage("Hello World");

			// Tell the producer to send the message
			producer.send(message);

			// Clean up
			session.close();
			connection.close();

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Sender();
	}
}