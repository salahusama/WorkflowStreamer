package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableLoginData;
import com.workflowstreamer.core.ImmutableNewUser;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
import com.workflowstreamer.dao.UsersDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
public class UsersResource {
    private UsersDAO usersDao;

    public UsersResource(UsersDAO usersDao) {
        this.usersDao = usersDao;
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableUser getUserById(@PathParam("id") int id) {
        return usersDao.getUserById(id);
    }

    @GET
    @Path("/user/{id}/stages")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableUserStage> getUserStagesForUser(@PathParam("id") int id) {
        return usersDao.getUserStagesByUserId(id);
    }

    @POST
    @Path("/user/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(ImmutableLoginData loginData) {
        Response.ResponseBuilder response;
        ImmutableUser user = usersDao.getUserByUsername(loginData.getUsername());
        if (user.getPassword().equals(loginData.getPassword())) {
            response = Response.ok(user);
        } else {
            response = Response.status(Response.Status.NOT_FOUND);
        }
        return response.build();
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(ImmutableNewUser newUser) {
        Response.ResponseBuilder response;
        try {
            int userId = usersDao.insertUser(newUser.getEmail(), newUser.getUsername(), newUser.getPassword());
            ImmutableUser insertedUser = usersDao.getUserById(userId);
            response = Response.ok(insertedUser);
        } catch (UnableToExecuteStatementException e) {
            response = Response.status(Response.Status.FORBIDDEN);
        }
        return response.build();
    }
}
