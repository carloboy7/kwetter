package jaxrs.resources;

import shared.restModels.User;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SuppressWarnings("RestParamTypeInspection")
@Path(value = "/users/{id}")
@Produces(TEXT_PLAIN)
public class SingleUserResource {

    public static User setHeataos(User user){
        if(user.getNextUrl() == null){
            user.setNextUrl(new ArrayList<>());
        }
        user.setResource("single_resource");
        user.getNextUrl().add("/users/"+user.getId()+"/followers");
        user.getNextUrl().add("/users/"+user.getId()+"/following");
        user.getNextUrl().add("/users/"+user.getId()+"/");
        user.getNextUrl().add("/users/"+user.getId()+"/kweets");
        user.getNextUrl().add("/users/"+user.getId()+"/id");
        user.getNextUrl().add("/users/"+user.getId()+"/name");
        user.getNextUrl().add("/users/"+user.getId()+"/bio");
        user.getNextUrl().add("/users/"+user.getId()+"/website");
        return user;
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public User showUserProfile(@PathParam("id") User user) {
        return user;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{field}")
    public Object selectSingleField(@PathParam("id") User user, @PathParam("field") String field){
        try {
            return SingleResourceHelper.selectSingleField(field, user);
        } catch (Exception ignored){
            throw new NotFoundException();
        }
    }
}