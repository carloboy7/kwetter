package auth_jaxrs.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import java.io.IOException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path(value = "/_local/auth")
@Produces(TEXT_PLAIN)
@Stateless
public class LocalAuth {

    @Inject
    private AuthEndpoint authEndpoint;

    @GET
    @Path("/login")
    public String getAuthHeader(@QueryParam("username") String username, @QueryParam("password") String password ){
        String result = authEndpoint.getAuthHeader(username, password);
        if(result == null || result.equals("wrong")){
            return "wrong";
        }
        return "Bearer " +result;

    }


    @GET
    @Path("/roles")
    @JWTTokenNeeded
    @Produces(APPLICATION_JSON)
    public Object getRoles( @Context SecurityContext securityContext) throws IOException {
        return authEndpoint.getRoles(securityContext);
    }
}
