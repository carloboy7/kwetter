package controller;

import com.carlo.framework.communication.ActiveMqConsumer;
import com.carlo.framework.communication.ActiveMqReplier;
import com.carlo.framework.interfaces.Listener;
import com.google.gson.Gson;
import shared.ActiveMq.ActiveMqSerializer;
import shared.restModels.UserIdProivder;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class JMSKickOff {

    private Gson gson = new Gson();

    @Inject
    private Router router;

    private ActiveMqConsumer kweetConsumer;
    private ActiveMqConsumer userConsumer;

    @PostConstruct
    public void receiving(){
        System.out.println("POST CONSTRUCT");
        this.kweetConsumer = new ActiveMqConsumer("kweet.new");
        this.userConsumer = new ActiveMqConsumer("user.new");

        this.kweetConsumer.getSerializationManager().setSerializer(Kweet.class, new ActiveMqSerializer<>(Kweet.class));
        this.userConsumer.getSerializationManager().setSerializer(User.class, new ActiveMqSerializer<>(User.class));

        this.kweetConsumer.addNewListener(new ToRouterListener(Kweet.class, "kweet.new"));
        this.userConsumer.addNewListener(new ToRouterListener(User.class, "user.new"));
    }

    private void handleMessage(int id, String queue, String message){
        router.messageReceived(queue, id, message);
    }

    private class ToRouterListener implements Listener {

        private Class acceptedType;
        private String queue;
        public ToRouterListener(Class acceptedType, String queue) {
            this.acceptedType = acceptedType;
            this.queue = queue;
        }

        @Override
        public Class getAcceptedType() {
            return acceptedType;
        }

        @Override
        public void onMessage(Object message, ActiveMqReplier replier) {
            UserIdProivder id = (UserIdProivder) message;
            handleMessage(id.getUserId(), queue, gson.toJson(message));
        }
    }

}
