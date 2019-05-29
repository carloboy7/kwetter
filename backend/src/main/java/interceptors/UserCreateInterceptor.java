package interceptors;

import com.carlo.framework.communication.ActiveMqProducer;
import entities.kweet.Kweet;
import entities.user.User;
import jaxrs.factories.KweetFactory;
import jaxrs.factories.UserFactory;
import shared.ActiveMq.ActiveMqSerializer;


import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

public class UserCreateInterceptor extends BaseInterceptor {
    public UserCreateInterceptor(){
        super("user.new" );
        this.producer.getSerializationManager().setSerializer(shared.restModels.User.class, new ActiveMqSerializer<shared.restModels.User>(shared.restModels.User.class));
    }


    @AroundInvoke
    public Object modify(InvocationContext ctx) throws Exception {
        if (ctx != null) {
            System.out.println("HET IS GELUKT!");
            Object[] parameters = ctx.getParameters();
            User entity = (User) parameters[0];
            super.sendObject(UserFactory.createUserFromEntity(entity));
            return ctx.proceed();
        } else {
            return null;
        }
    }
}
