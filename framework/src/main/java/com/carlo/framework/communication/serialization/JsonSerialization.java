package com.carlo.framework.communication.serialization;

import com.google.gson.Gson;

public class JsonSerialization {
    public static String FromObject(Serializer serializer, Object obj ){
        CommunicationTemplate template = new CommunicationTemplate();
        template.setClassName(obj.getClass().getName());
        template.setData(serializer.SerialiseFromObject(obj));
        return new Gson().toJson(template);
    }
    public static CommunicationTemplate FromJson(String data){
        return new Gson().fromJson(data, CommunicationTemplate.class);
    }
}
