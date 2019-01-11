package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableLoginData;
import com.workflowstreamer.core.ImmutableNewUser;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
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
    public Set<ImmutableUserStage> getUserStagesForUser(@PathParam("id") int id) {
        return usersManager.getUserStagesByUserId(id);
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
