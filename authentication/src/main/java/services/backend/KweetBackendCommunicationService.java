package services.backend;

import factory.UserFactory;
import org.apache.http.client.utils.URIBuilder;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;
import static services.helpers.RestCommuncationHelper.getResponseString;

@SuppressWarnings("unchecked")
@Stateless
public class KweetBackendCommunicationService extends BackendCommunication {

    private static final String GET_KWEET = "http://backend_server:8080/Kwetter/_local/v1/kweet/%d";
    public Kweet getKweet(int id) throws IOException {
        return (Kweet) callUrlAndCastResult(Kweet.class, GET_KWEET, id);
    }
    public void deleteKweet(int id) throws IOException {
        getResponseString(String.format(GET_KWEET, id), "DELETE");
    }
    public List<Kweet> searchKweet(String query) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("backend_server");
        builder.setPort(8080);
        builder.setPath("/Kwetter/_local/v1/kweets/search");
        builder.addParameter("query", query);

        return (List<Kweet>) callUrlAndCastResult((new TypeLiteral<List<Kweet>>(){}).getType(), builder.build().toURL().toString(), query);
    }
}
