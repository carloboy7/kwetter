package interceptors;

import entities.kweet.Kweet;
import jaxrs.factories.KweetFactory;
import shared.ActiveMq.ActiveMqSerializer;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class KweetCreateInterceptor extends BaseInterceptor {
    public KweetCreateInterceptor(){
        super("kweet.new" );
        this.producer.getSerializationManager().setSerializer(shared.restModels.Kweet.class, new ActiveMqSerializer<shared.restModels.Kweet>(shared.restModels.Kweet.class));
    }

    @AroundInvoke
    public Object modify(InvocationContext ctx) throws Exception {
        if (ctx != null) {
            Object[] parameters = ctx.getParameters();
            Kweet entity = (Kweet) parameters[0];

            super.sendObject(KweetFactory.createKweetFromEntity(entity));
            return ctx.proceed();
        } else {
            return null;
        }
    }
}
