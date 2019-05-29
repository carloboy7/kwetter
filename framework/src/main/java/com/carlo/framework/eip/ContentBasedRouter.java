package com.carlo.framework.eip;

import com.carlo.framework.interfaces.ObjectConsumer;
import com.carlo.framework.interfaces.ObjectProducer;

import java.util.ArrayList;
import java.util.List;

public abstract class ContentBasedRouter implements ObjectConsumer, ObjectProducer {
    private List<ObjectConsumer> consumers = new ArrayList<>();

    @Override
    public void consume(Object object) {
        for (ObjectConsumer consumer : consumers) {
            if(contentHit(object, consumer)){
                consumer.consume(object);
                return;
            }
        }
    }

    protected abstract boolean contentHit(Object object, ObjectConsumer consumer);

    @Override
    public void addNewListener(ObjectConsumer consumer) {
        consumers.add(consumer);
    }
}
