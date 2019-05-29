package interceptors;

import com.carlo.framework.communication.ActiveMqProducer;
import com.carlo.framework.communication.serialization.Serializer;
import shared.ActiveMq.ActiveMqSerializer;
import shared.restModels.User;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

abstract class BaseInterceptor {
    protected ActiveMqProducer producer;
    private Logger logger = Logger.getLogger(UserCreateInterceptor.class.getName());
    protected BaseInterceptor(String queue){
        this.producer = new ActiveMqProducer(queue);
    }
    protected void sendObject(Object object) throws Exception {
        this.producer.send(object);
    }
}
