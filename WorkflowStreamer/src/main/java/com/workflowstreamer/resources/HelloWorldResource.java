package com.workflowstreamer.resources;

import javax.ws.rs.*;
import java.util.Optional;

@Path("/hello")
public class HelloWorldResource {
    @GET
    public String sayHello(@QueryParam("name") Optional<String> optionalName) {
        return String.format("Hello, %s", optionalName.orElse("Stranger"));
    }
}
