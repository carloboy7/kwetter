package jaxrs.resources;

import jaxrs.factories.UserFactory;
import services.UserService;
import shared.restModels.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(value = "/user")
@Stateless
public class CreateUserResource {
    @Inject
    UserService service;

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createUser(User user){
        UserFactory factory = new UserFactory();
        entities.user.User entity = factory.createEntityFromUser(user);
        entity.setId(user.getId());
        entity.setWebsite("");
        entity.setBio("");
        service.create(entity);
        return true;
    }
}
