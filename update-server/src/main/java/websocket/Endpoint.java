package websocket;

import com.google.gson.Gson;
import controller.JMSKickOff;
import controller.Router;
import decoders.Decode;
import shared.websocket.QueueRequest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.Path;

@ServerEndpoint(value = "/update", decoders = {Decode.class})
@Stateless
public class Endpoint {

    @Inject
    private JMSKickOff receiver;

    @Inject
    private Router router;

    @OnMessage
    public void onMessage(Session session, QueueRequest msg) {
        router.setQueueListener(msg.getQueueName(), session);
    }

    @OnClose
    public void onClose(Session session){
        router.removeQueueListener(session);
    }
}
