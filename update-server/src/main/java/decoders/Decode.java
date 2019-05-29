package decoders;

import com.google.gson.Gson;
import shared.websocket.QueueRequest;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class Decode implements Decoder.Text<QueueRequest> {
    private Gson gson = new Gson();
    @Override
    public QueueRequest decode(String s) throws DecodeException {
        return gson.fromJson(s, QueueRequest.class);
    }

    @Override
    public boolean willDecode(String s) {
        try {
            return decode(s) != null;
        } catch (DecodeException e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
