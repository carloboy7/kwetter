package auth_jaxrs;

import services.backend.UserBackendCommunicationService;
import shared.restModels.Kweet;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.io.IOException;

@RequestScoped
public class UserInformationProvider {

    @Inject
    protected UserBackendCommunicationService backendEndpoint;

    protected Object kweets(int id) throws IOException {
        return backendEndpoint.getKweets(id);
    }

    protected Object followers(int id ) throws IOException {
        return backendEndpoint.getFollowers(id);
    }

    protected Object following( int id) throws IOException {
        return backendEndpoint.getFollowing(id);
    }

    protected Object singleUser(int id) throws IOException {
        return backendEndpoint.singleUser(id);
    }

}
