package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableLoginData;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.dao.UsersDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersResource {
    private UsersDAO usersDao;

    public UsersResource(UsersDAO usersDao) {
        this.usersDao = usersDao;
    }

    @POST
    @Path("/login")
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
}
