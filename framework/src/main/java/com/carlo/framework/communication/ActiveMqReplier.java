package com.carlo.framework.communication;

import com.carlo.framework.communication.serialization.SerializationManager;


import javax.jms.*;

public class ActiveMqReplier {
    private Message originalMessage;
    private SerializationManager serializationManager;
    private MessageProducer producer;
    private Session session;

    public ActiveMqReplier(Message message, SerializationManager serializationManager, MessageProducer producer, Session session) {
        this.originalMessage = message;
        this.serializationManager = serializationManager;
        this.producer = producer;
        this.session = session;
    }
    public void reply(Object object){

        TextMessage response = null;
        try {
            response = this.session.createTextMessage();
            response.setJMSCorrelationID(originalMessage.getJMSCorrelationID());
            response.setText(this.serializationManager.getSerializedString(object));
            try{
                int id = originalMessage.getIntProperty("aggregateId");
                response.setIntProperty("aggregateId", id);
            }catch (Exception ignored){}
            this.producer.send(originalMessage.getJMSReplyTo(), response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
