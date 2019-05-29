package auth_jaxrs;

import auth_jaxrs.auth.JWTTokenNeeded;
import factory.UserFactory;
import shared.restModels.Kweet;
import shared.restModels.User;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Path(value = "/me")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class MeEndpoint extends UserInformationProvider {

    @Inject
    private UserFactory factory;
    
    @Path("/kweets")
    @GET
    @JWTTokenNeeded
    public Object kweets(@Context SecurityContext securityContext) throws IOException {
        return super.kweets(getUserId(securityContext));
    }

    @POST
    @Path("/kweet")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Object postKweet(@Context SecurityContext securityContext, @NotNull Kweet kweet) throws IOException {
        return backendEndpoint.postKweet(getUserId(securityContext), kweet);
    }

    @POST
    @Path("/following/{id}")
    @JWTTokenNeeded
    public Object followUser(@PathParam("id") int accountToFollow, @Context SecurityContext securityContext) throws IOException {
        return backendEndpoint.followUser(accountToFollow, getUserId(securityContext));
    }

    @DELETE
    @Path("/following/{id}")
    @JWTTokenNeeded
    public Object deleteFollowUser(@PathParam("id") int accountToFollow, @Context SecurityContext securityContext) throws IOException {
        return backendEndpoint.unfollowUser(accountToFollow, getUserId(securityContext));
    }


    @GET
    @Path("/followers")
    @JWTTokenNeeded
    public Object followers(@Context SecurityContext securityContext ) throws IOException {
        return super.followers(getUserId(securityContext));
    }

    @GET
    @Path("/following")
    @JWTTokenNeeded
    public Object following( @Context SecurityContext securityContext) throws IOException {
        return super.following(getUserId(securityContext));
    }


    @SuppressWarnings("unchecked")
    @GET
    @Path("/dashboard")
    @JWTTokenNeeded
    public Object dashboard(@Context SecurityContext securityContext ) throws IOException {
        List<User> users = ((List<User>) following(securityContext));
        if(users == null){
            users = new ArrayList<>();
        }
        users.add((User) super.singleUser(getUserId(securityContext)));
        return users.stream().map(x -> {
            try {
                if(x == null){
                    return Collections.emptyList();
                }
                return (List<Kweet>) super.kweets(x.getId());
            } catch (IOException e) {
                return Collections.emptyList();
            }
        }).collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @GET
    @Path("")
    @JWTTokenNeeded
    public Object singleUser(@Context SecurityContext securityContext) throws IOException {
        return super.singleUser(getUserId(securityContext));
    }

    private int getUserId(SecurityContext securityContext){
        return factory.createUserFromRequest(securityContext).getId();
    }


}
