package com.workflowstreamer.resources;

import com.workflowstreamer.core.*;
import com.workflowstreamer.manager.UsersManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
public class UsersResource {
    private UsersManager usersManager;

    public UsersResource(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableUser getUserById(@PathParam("id") int id) {
        return usersManager.getUserById(id);
    }

    @GET
    @Path("/user/{id}/stages")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableStage> getUserStagesForUser(@PathParam("id") int id) {
        return usersManager.getStages();
    }

    @POST
    @Path("/user/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(ImmutableLoginData loginData) {
        return usersManager.login(loginData);
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(ImmutableNewUser newUser) {
        return usersManager.addNewUser(newUser);
    }
}
