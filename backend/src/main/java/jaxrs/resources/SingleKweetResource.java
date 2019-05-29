package jaxrs.resources;

import services.KweetService;
import shared.restModels.Kweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SuppressWarnings("RestParamTypeInspection")
@Path(value = "/kweet/{id}")
@Produces(TEXT_PLAIN)
@Stateless
public class SingleKweetResource {

    public static Kweet  setHeataos(Kweet kweet){
        kweet.setResource("single_resource");
        if(kweet.getNextUrl() == null){
            kweet.setNextUrl(new ArrayList<>());
        }
        kweet.getNextUrl().add("/kweet/"+kweet.getId()+"/text");
        kweet.getNextUrl().add("/kweet/"+kweet.getId()+"/user");
        kweet.getNextUrl().add("/kweet/"+kweet.getId()+"/id");
        kweet.getNextUrl().add("/kweet/"+kweet.getId()+"/");
        SingleUserResource.setHeataos(kweet.getUser());
        return kweet;
    }

    @Inject
    private KweetService service;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Valid
    public Kweet getKweet(@PathParam("id") Kweet kweet) {
        setHeataos(kweet);
        kweet.setUrl("/kweet/"+kweet.getId());
        return kweet;
    }


    @DELETE
    @Path("")
    @Valid
    public boolean deleteKweet(@PathParam("id") entities.kweet.Kweet kweet) {
        service.delete(service.getById(kweet.getId()));
        return true;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{field}")
    public Object selectSingleField(@PathParam("id") Kweet kweet, @PathParam("field") String field){
        try {
            return SingleResourceHelper.selectSingleField(field, kweet);
        } catch (Exception ignored){
            throw new NotFoundException();
        }
    }
}