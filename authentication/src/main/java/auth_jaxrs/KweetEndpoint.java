package auth_jaxrs;

import auth_jaxrs.auth.JWTTokenNeeded;
import services.backend.KweetBackendCommunicationService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.net.URISyntaxException;


@Path(value = "/kweet")
@Stateless
public class KweetEndpoint {

    @Inject
    KweetBackendCommunicationService backend;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object basic(@PathParam("id") int id) throws IOException {
        return backend.getKweet(id);
    }

    @DELETE
    @Path("/{id}")
    @Valid
    @JWTTokenNeeded
    public boolean deleteKweet(@PathParam("id") int id, @Context SecurityContext securityContext) throws IOException {
        if(securityContext.isUserInRole("moderator") || securityContext.isUserInRole("admin")) {
            backend.deleteKweet(id);
        }
        return true;
    }

    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Object basic(@QueryParam("query") String query) throws IOException, URISyntaxException {
        return backend.searchKweet(query);
    }
}
