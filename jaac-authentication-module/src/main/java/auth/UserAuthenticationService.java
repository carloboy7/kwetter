package auth;
import org.apache.http.client.utils.URIBuilder;
import shared.communication.*;
import shared.restModels.Role;
import shared.restModels.User;

import javax.enterprise.util.TypeLiteral;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserAuthenticationService implements IUserAuthenticationService, AuthProvider
{
    private UnAuthorizedHttpConnection unAuthorizedConnection;
    private AuthorizedHttpConnection authorizedConnection;

    private String cachedAuthHeader;
    private Jsonb jsonb;

    UserAuthenticationService() {
        jsonb = JsonbBuilder.create();;
        unAuthorizedConnection = new UnAuthorizedHttpConnection();
        authorizedConnection = new AuthorizedHttpConnection(this);
    }

    @Override
    public void validatePassword(String usid, String password) throws Exception
    {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("authentication");
        builder.setPort(8080);
        builder.setPath("/Kwetter/v1/_local/auth/login");
        builder.addParameter("username", usid);
        builder.addParameter("password", password);

        HttpURLConnection connection = unAuthorizedConnection.getConnection(builder.build().toURL());
        connection.setRequestMethod("GET");
        String result = ResponseHelper.getResponseFromConnection(connection);

        if(result == null || result.equals("wrong")){
            throw new Exception("Login not correct");
        }
        cachedAuthHeader = result;
    }

    @Override
    public List<String> getGroups() throws Exception
    {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("authentication");
        builder.setPort(8080);
        builder.setPath("/Kwetter/v1/_local/auth/roles");

        HttpURLConnection connection = authorizedConnection.getConnection(builder.build().toURL());
        connection.setRequestMethod("GET");
        return
                new ArrayList<Role>(jsonb.fromJson(ResponseHelper.getResponseFromConnection(connection), (new TypeLiteral<List<Role>>(){}).getType()))
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String getAuthHeader() {
        return cachedAuthHeader;
    }
}