package jaxrs.resources;

import jaxrs.factories.UserFactory;
import services.UserService;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;


@Path(value = "/users/search")
@Stateless
public class UsersResource {

    @Inject
    private UserService userService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public List<User> getAllUsers(@QueryParam("query") String query) {
        return userService.searchUsersFor(query).stream().map(UserFactory::createUserFromEntity).map(SingleUserResource::setHeataos).collect(Collectors.toList());
    }
}
