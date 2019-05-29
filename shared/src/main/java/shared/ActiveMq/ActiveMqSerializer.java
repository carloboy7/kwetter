package shared.ActiveMq;

import com.google.gson.Gson;

public class ActiveMqSerializer<T> implements com.carlo.framework.communication.serialization.Serializer<T>{

    private Gson gson = new Gson();

    private Class<T> acceptedType;

    public ActiveMqSerializer(Class<T> acceptedType) {
        this.acceptedType = acceptedType;
    }

    @Override
    public String SerialiseFromObject(T object) {
        return gson.toJson(object);
    }

    @Override
    public T SerialiseFromString(String data) {
        return (T) gson.fromJson(data, acceptedType);
    }
}
