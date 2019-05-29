package com.carlo.framework.communication.serialization;

public interface Serializer<T> {
    String SerialiseFromObject(T object);
    T SerialiseFromString(String data);
}
