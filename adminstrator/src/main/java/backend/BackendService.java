package backend;


import auth.AuthProvider;
import org.apache.http.client.utils.URIBuilder;
import shared.communication.AuthorizedHttpConnection;
import shared.communication.ResponseHelper;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.enterprise.util.TypeLiteral;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class BackendService implements Serializable {
    private AuthorizedHttpConnection connection;
    protected Jsonb jsonb = JsonbBuilder.create();

    private static Logger LOG = Logger.getLogger(BackendService.class.getName());

    public BackendService(){
        connection = new AuthorizedHttpConnection(new AuthProvider());
    }

    public List<Kweet> searchKweets(String query) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("authentication");
        builder.setPort(8080);
        builder.setPath("/Kwetter/v1/kweet/search");
        builder.addParameter("query", query);

        HttpURLConnection http = connection.getConnection(builder.build().toURL());
        http.setRequestMethod("GET");

        LOG.info("make request to "+ builder.build().toURL());
        return jsonb.fromJson(ResponseHelper.getResponseFromConnection(http), (new TypeLiteral<List<Kweet>>(){}).getType());
    }
    public List<User> searchUsers(String query) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("authentication");
        builder.setPort(8080);
        builder.addParameter("query", query);
        builder.setPath("/Kwetter/v1/users/search");

        HttpURLConnection http = connection.getConnection(builder.build().toURL());
        http.setRequestMethod("GET");
        return jsonb.fromJson(ResponseHelper.getResponseFromConnection(http), (new TypeLiteral<List<User>>(){}).getType());
    }


    public void deleteKweet(int id) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("authentication");
        builder.setPort(8080);
        builder.setPath("/Kwetter/v1/kweet/" + id);

        HttpURLConnection http = connection.getConnection(builder.build().toURL());
        http.setRequestMethod("DELETE");

        LOG.info("make request to "+ builder.build().toURL());
        ResponseHelper.getResponseFromConnection(http);
    }
}
