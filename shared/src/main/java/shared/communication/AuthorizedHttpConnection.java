package shared.communication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class AuthorizedHttpConnection implements HttpCommunicator {

    private AuthProvider provider;
    private UnAuthorizedHttpConnection unAuthorizedHttpConnection;

    public AuthorizedHttpConnection(AuthProvider provider) {
        this.provider = provider;
        this.unAuthorizedHttpConnection = new UnAuthorizedHttpConnection();
    }

    @Override
    public HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection connection = this.unAuthorizedHttpConnection.getConnection(url);
        connection.setRequestProperty(AUTHORIZATION, provider.getAuthHeader());
        return connection;
    }
}
