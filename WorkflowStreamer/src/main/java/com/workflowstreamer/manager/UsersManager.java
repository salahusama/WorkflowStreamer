package com.workflowstreamer.manager;

import com.workflowstreamer.core.ImmutableLoginData;
import com.workflowstreamer.core.ImmutableNewUser;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
import com.workflowstreamer.dao.UsersDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.util.Set;

public class UsersManager {
    private final UsersDAO usersDao;

    public UsersManager(UsersDAO usersDao) {
        this.usersDao = usersDao;
    }

    public ImmutableUser getUserById(int id) {
        return usersDao.getUserById(id);
    }

    public Set<ImmutableUserStage> getUserStagesByUserId(int userId) {
        return usersDao.getUserStagesByUserId(userId);
    }

    public Response login(ImmutableLoginData loginData) {
        Response.ResponseBuilder response;
        ImmutableUser user = usersDao.getUserByUsername(loginData.getUsername());

        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            response = Response.ok(user);
        } else {
            response = Response.status(Response.Status.NOT_FOUND);
        }

        return response.build();
    }

    public Response addNewUser(ImmutableNewUser newUser) {
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
