package com.carlo.framework.communication;

import org.apache.activemq.broker.BrokerService;

public class ActiveMqBroker {
    private BrokerService broker;
    public void start(){
        broker = new BrokerService();
        try {
            broker.addConnector("tcp://localhost:61616");
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
