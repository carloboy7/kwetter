package com.carlo.framework.communication;

import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

public class ActiveMqReplyListenerCollection {
    private List<ReplyListener> listeners;
    private Object original;
    public ActiveMqReplyListenerCollection(Object original){
        listeners = new ArrayList<>();
        this.original = original;

    }

    public void addListener(ReplyListener listener){
        listeners.add(listener);
    }

    public void onMessage(Object response, Message message) {
        for (ReplyListener listener : listeners) {
            if (listener.getAcceptedType().getName().equals(response.getClass().getName())) {
                listener.onMessage(response, original, message);
            }
        }

    }
}
