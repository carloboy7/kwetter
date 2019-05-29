package com.carlo.framework.eip;

import com.carlo.framework.interfaces.ObjectProducer;
import com.carlo.framework.interfaces.ObjectConsumer;

import java.util.ArrayList;
import java.util.List;

public abstract class Enricher implements ObjectProducer, ObjectConsumer {
    private List<ObjectConsumer> consumers = new ArrayList<>();
    @Override
    public void consume(Object object) {
        Object obj = enrich(object);
        consumers.forEach(x-> x.consume(obj));
    }

    protected abstract Object enrich(Object object);

    @Override
    public void addNewListener(ObjectConsumer consumer) {
        consumers.add(consumer);
    }
}
