package shared.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpCommunicator {
    HttpURLConnection getConnection(URL url) throws IOException;
}
