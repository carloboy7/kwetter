package com.carlo.framework.eip;

import com.carlo.framework.interfaces.ObjectConsumer;
import com.carlo.framework.interfaces.ObjectProducer;

import java.util.ArrayList;
import java.util.List;

public class ReceiptList implements ObjectConsumer, ObjectProducer {

    private List<ObjectConsumer> consumers = new ArrayList<>();

    @Override
    public void consume(Object object) {
        consumers.forEach(x -> x.consume(object));
    }

    @Override
    public void addNewListener(ObjectConsumer consumer) {
        consumers.add(consumer);
    }
}
