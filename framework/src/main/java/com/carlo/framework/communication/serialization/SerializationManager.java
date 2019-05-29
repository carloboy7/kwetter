package com.carlo.framework.communication.serialization;

import java.util.HashMap;
import java.util.Map;

public class SerializationManager {

    protected Map<String, Serializer> serializers;
    public SerializationManager(){
        serializers = new HashMap<>();
    }
    public <T> void setSerializer(Class<T> produces, Serializer<T> serializer){
        this.serializers.put(produces.getName(), serializer);
    }
    public void removeSerializer(Class produces){
        this.serializers.remove(produces.getName());
    }

    public Object getInstanceFromMessage(String message){
        CommunicationTemplate template = JsonSerialization.FromJson(message);

        if (serializers.containsKey(template.getClassName())) {
            Serializer<?> instance = serializers.get(template.getClassName());
            return instance.SerialiseFromString(template.getData());
        }
        return null;
    }
    public String getSerializedString(Object request) {
        if(serializers.get(request.getClass().getName()) == null){
            return null;
        }
        return JsonSerialization.FromObject(serializers.get(request.getClass().getName()), request);
    }
}
