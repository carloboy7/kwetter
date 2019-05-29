package shared.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UnAuthorizedHttpConnection implements HttpCommunicator {

    @Override
    public HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
}
