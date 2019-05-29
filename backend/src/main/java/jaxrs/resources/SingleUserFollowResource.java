package jaxrs.resources;

import jaxrs.factories.UserFactory;
import shared.restModels.Kweet;
import shared.restModels.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SuppressWarnings("RestParamTypeInspection")
@Path(value = "/users/{id}")
@Produces(TEXT_PLAIN)
@Stateless
public class SingleUserFollowResource {


    @Inject
    private UserService userService;

    @GET
    @Path("/followers")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public List<User> getFollowers(@PathParam("id") entities.user.User entity) {
        return entity.getFollowers().stream().map(UserFactory::createUserFromEntity).map(SingleUserResource::setHeataos).collect(Collectors.toList());
    }

    @POST
    @Path("/followers/{followerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean postFollowers(@PathParam("id") entities.user.User entity, @PathParam("followerId") entities.user.User follower) {
        return userService.addFollowerToUser(entity, follower);
    }

    @DELETE
    @Path("/followers/{followerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteFollowers(@PathParam("id") entities.user.User entity, @PathParam("followerId") entities.user.User follower) {
        Logger.getAnonymousLogger().info("Delete follower with id " + follower.getId());
        return userService.removeFollowerFromUser(entity, follower);
    }

    @GET
    @Path("/following")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public List<User> getFollowing(@PathParam("id") entities.user.User entity) {
        return entity.getFollowing().stream().map(UserFactory::createUserFromEntity).map(SingleUserResource::setHeataos).collect(Collectors.toList());
    }

}