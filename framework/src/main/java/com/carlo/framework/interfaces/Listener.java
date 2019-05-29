package com.carlo.framework.interfaces;

import com.carlo.framework.communication.ActiveMqReplier;

public interface Listener<T> {
    /**
     * Used by the Consumer framework to filter listeners based on their accepted types.
     * Inheritance is not supported
     * @return
     */
    Class<T> getAcceptedType();

    /**
     * Called by the Consumer Thread when a message with the type T has arrived
     * @param message
     */
    void onMessage(T message, ActiveMqReplier replier);
}
