package com.carlo.framework.communication;

import com.carlo.framework.communication.serialization.SerializationManager;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

abstract class AbstractActiveMqCommunicator {
    protected Session session;
    protected Connection connection;
    protected Destination destination;
    protected SerializationManager serializationManager;
    protected AbstractActiveMqCommunicator(){
        serializationManager = new SerializationManager();
    }
    protected void initiateSessionAndDestination(String queueName) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://activemq:61616");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        connection = connectionFactory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        destination = session.createQueue(queueName);
    }

    public SerializationManager getSerializationManager() {
        return serializationManager;
    }
}
