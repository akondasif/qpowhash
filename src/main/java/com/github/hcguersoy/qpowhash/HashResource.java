package com.github.hcguersoy.qpowhash;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HashResource {

    @Inject
    HashService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{input}")
    public String hash(@PathParam String input) {
        return service.hash(input);
    }
}