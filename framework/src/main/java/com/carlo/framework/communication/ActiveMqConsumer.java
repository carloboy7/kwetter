package com.carlo.framework.communication;

import com.carlo.framework.interfaces.Listener;
import com.carlo.framework.interfaces.ObjectConsumer;
import com.carlo.framework.interfaces.ObjectProducer;
import sun.rmi.runtime.Log;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ActiveMqConsumer extends AbstractActiveMqCommunicator implements MessageListener, ObjectProducer {

    private MessageConsumer consumer;
    private String queueName;
    private final List<Listener> listeners;
    private final List<ObjectConsumer> objectConsumers;
    private MessageProducer replyProducer;

    private Logger log = Logger.getLogger(ActiveMqConsumer.class.getName());

    public ActiveMqConsumer(String queueName){
        super();
        this.queueName = queueName;
        listeners = new ArrayList<>();
        objectConsumers = new ArrayList<>();
        try {
            initiateSessionAndDestination(queueName);
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);

            replyProducer = this.session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



    private void sendToListeners(String message, Message original) throws JMSException {
        Object obj = serializationManager.getInstanceFromMessage(message);
        if(obj == null){
            return;
        }
        for (Listener listener : listeners) {
            if (listener.getAcceptedType().getName().equals(obj.getClass().getName())) {
                listener.onMessage(obj, new ActiveMqReplier(original, serializationManager, replyProducer, session));
            }
        }
        objectConsumers.forEach(x->x.consume(obj));
        original.acknowledge();
    }

    private void close(){
        if(session != null) {
            // Clean up
            try {
                consumer.close();
                session.close();
                connection.close();
            } catch (JMSException ignore) {}
        }
    }

    public void addNewListener(Listener listener){
        listeners.add(listener);
    }


    @Override
    public void onMessage(Message message) {
        try {
        String result;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            result = textMessage.getText();
            } else {
                result = message.toString();
            }
            log.info(result);
            sendToListeners(result, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addNewListener(ObjectConsumer consumer) {
        objectConsumers.add(consumer);
    }
}
