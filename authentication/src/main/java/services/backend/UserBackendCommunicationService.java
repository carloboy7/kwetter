package services.backend;


import org.apache.http.client.utils.URIBuilder;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.util.TypeLiteral;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import static services.helpers.RestCommuncationHelper.getResponseString;
import static services.helpers.RestCommuncationHelper.postRequest;


@SuppressWarnings("unchecked")
@Stateless
@Dependent
public class UserBackendCommunicationService extends BackendCommunication implements Serializable  {



    private static final String GET_KWEETS = "http://backend_server:8080/Kwetter/_local/v1/users/%d/kweets";
    private static final String GET_FOLLOWERS = "http://backend_server:8080/Kwetter/_local/v1/users/%d/followers";
    private static final String GET_FOLLOWING = "http://backend_server:8080/Kwetter/_local/v1/users/%d/following";
    private static final String SINGLE_USER = "http://backend_server:8080/Kwetter/_local/v1/users/%d";
    private static final String POST_KWEET = "http://backend_server:8080/Kwetter/_local/v1/users/%d/kweet";
    private static final String POST_FOLLOWER = "http://backend_server:8080/Kwetter/_local/v1/users/%d/followers/%d";
    private static final String POST_CREATE_USER = "http://backend_server:8080/Kwetter/_local/v1/user";



    public List<Kweet> getKweets(int userId) throws IOException {
        return (List<Kweet>) callUrlAndCastResult((new TypeLiteral<List<Kweet>>(){}).getType(), GET_KWEETS, userId);
    }

    public List<User> getFollowers(int userId) throws IOException {
        return (List<User>) callUrlAndCastResult((new TypeLiteral<List<User>>(){}).getType(), GET_FOLLOWERS, userId);
    }

    public List<User> getFollowing(int userId) throws IOException {
        return (List<User>) callUrlAndCastResult((new TypeLiteral<List<User>>(){}).getType(), GET_FOLLOWING, userId);
    }

    public User singleUser(int userId) throws IOException {
        return (User) callUrlAndCastResult(User.class, SINGLE_USER, userId);
    }

    public Object followUser(int originalId, int followerId) throws IOException {
        return postRequest(String.format(POST_FOLLOWER, originalId, followerId), "");
    }

    public Object unfollowUser(int originalId, int followerId) throws IOException {
        Logger.getAnonymousLogger().info("Delete follower " + followerId);
        return getResponseString(String.format(POST_FOLLOWER, originalId, followerId), "DELETE");
    }

    public Kweet postKweet(int userId, Kweet kweet) throws IOException {
        String result = postRequest(String.format(POST_KWEET, userId), jsonb.toJson(kweet));

        if (result != null) {
            return jsonb.fromJson(result, Kweet.class);
        } else {
            return null;
        }
    }


    public List<User> search(String query) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("backend_server");
        builder.setPort(8080);
        builder.addParameter("query", query);
        builder.setPath("/Kwetter/_local/v1/users/search");


        return (List<User>) callUrlAndCastResult((new TypeLiteral<List<User>>(){}).getType(), builder.build().toURL().toString(), query);

    }

    public Object createUser(User user) throws IOException {
        return postRequest(POST_CREATE_USER, jsonb.toJson(user));
    }
}
