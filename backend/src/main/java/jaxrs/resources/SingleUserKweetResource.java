package jaxrs.resources;

import jaxrs.factories.KweetFactory;
import shared.restModels.Kweet;
import services.KweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SuppressWarnings("RestParamTypeInspection")
@Path(value = "/users/{id}")
@Produces(TEXT_PLAIN)
@Stateless
public class SingleUserKweetResource {

    @Inject
    private KweetService kweetService;

    @GET
    @Path("/kweets")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public List<Kweet> getKweetsFromUser(@PathParam("id") entities.user.User entity) {
        return kweetService.getAllKweetsFromUser(entity).stream().map(KweetFactory::createKweetFromEntity).map(SingleKweetResource::setHeataos).collect(Collectors.toList());
    }

    @POST
    @Path("/kweet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Valid
    public Kweet postKweet(@PathParam("id") entities.user.User entityUser, @NotNull Kweet kweet) {
        try{
            kweet.setId(0);
            kweet.setUser(null);
            entities.kweet.Kweet entityKweet = KweetFactory.createEntityFromKweet(kweet);
            entityKweet.setUser(entityUser);
            kweetService.create(entityKweet);
            return KweetFactory.createKweetFromEntity(entityKweet);
        }catch (Exception e){
            throw new BadRequestException("Invalid Kweet entry");
        }

    }

}