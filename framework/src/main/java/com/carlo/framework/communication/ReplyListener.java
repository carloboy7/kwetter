package com.carlo.framework.communication;

import javax.jms.Message;

public interface ReplyListener<T> {
    /**
     * Used by the Consumer framework to filter listeners based on their accepted types.
     * Inheritance is not supported
     * @return
     */
    Class<T> getAcceptedType();

    /**
     * Called by the Consumer Thread when a message with the type T has arrived
     * @param response
     */
    void onMessage(T response, Object original, Message message);
}
