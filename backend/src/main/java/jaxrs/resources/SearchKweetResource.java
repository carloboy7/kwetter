package jaxrs.resources;

import jaxrs.factories.KweetFactory;
import services.KweetService;
import shared.restModels.Kweet;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@SuppressWarnings("RestParamTypeInspection")
@Path(value = "/kweets/")
@Produces(TEXT_PLAIN)
public class SearchKweetResource {

    @Inject
    private KweetService service;

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Kweet> searchKweets(@QueryParam("query") String query) {
        return service.searchKweetsFor(query).stream().map(KweetFactory::createKweetFromEntity).map(SingleKweetResource::setHeataos).collect(Collectors.toList());
    }

}