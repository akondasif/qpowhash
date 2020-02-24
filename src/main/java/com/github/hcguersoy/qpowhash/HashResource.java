package com.github.hcguersoy.qpowhash;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("/")
public class HashResource {

    @Inject
    HashService service;

    private final Logger log = Logger.getLogger(HashResource.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{input}")
    public String hash(@PathParam String input) {
        return service.hash(input);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.WILDCARD)
    public String hashFromBody(String input) {
        String hashed = service.hash(input);
        log.info("Hash for [" + input + "] is [" + hashed + "].");
        return hashed;
    }
}