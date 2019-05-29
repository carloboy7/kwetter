package com.carlo.framework.communication;

import com.carlo.framework.interfaces.ObjectConsumer;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ActiveMqProducer extends AbstractActiveMqCommunicator implements MessageListener, ObjectConsumer {

    private Destination replyDestination;
    private MessageConsumer responseConsumer;
    private String queueName;
    private MessageProducer producer;
    private Map<String, ActiveMqReplyListenerCollection> replyCollection;


    public ActiveMqProducer(String queueName){
        super();
        this.queueName = queueName;
        this.replyCollection = new HashMap<>();

        try {
            initiateSessionAndDestination(this.queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            replyDestination = session.createTemporaryQueue();
            responseConsumer = session.createConsumer(replyDestination);
            responseConsumer.setMessageListener(this);


        } catch (JMSException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    public void close(){
        if(session != null) {
            // Clean up
            try {
                session.close();
                connection.close();
            } catch (JMSException ignore) {}
        }
    }

    private TextMessage createTextMessageWithCorrelation(Object request) throws JMSException {
        String data = serializationManager.getSerializedString(request);
        if(data == null){
            return null;
        }

        TextMessage message = session.createTextMessage(data);
        String correlationId = this.createRandomString();

        message.setJMSReplyTo(replyDestination);
        message.setJMSCorrelationID(correlationId);
        return message;
    }

    public ActiveMqReplyListenerCollection send(Object request){
        // Create a messages
        try {
            TextMessage message = createTextMessageWithCorrelation(request);
            System.out.println("SEND: "+ message);
            if(message == null){
                return null;
            }
            System.out.println(message.getText());
            producer.send(message);
            return createReplyListener(request, message.getJMSCorrelationID());

        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ActiveMqReplyListenerCollection send(Object request, int aggregateId){
        try {
            TextMessage message = createTextMessageWithCorrelation(request);
            if(message == null){
                return null;
            }
            message.setIntProperty("aggregateId", aggregateId);
            producer.send(message);
            return createReplyListener(request, message.getJMSCorrelationID());

        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

    private ActiveMqReplyListenerCollection createReplyListener(Object original, String id){
        ActiveMqReplyListenerCollection col = new ActiveMqReplyListenerCollection(original);
        replyCollection.put(id, col);
        return col;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        try {
            ActiveMqReplyListenerCollection collection = replyCollection.get(message.getJMSCorrelationID());
            if(collection != null){
                collection.onMessage(serializationManager.getInstanceFromMessage(((TextMessage) message).getText()), message);
            }
        } catch (JMSException ignored) {}
    }

    @Override
    public void consume(Object object) {
        send(object);
    }
}
