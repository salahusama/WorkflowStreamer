package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;
import com.workflowstreamer.dao.UsersDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;

@Path("/tasks")
public class TasksResource {
    private TasksDAO tasksDao;

    public TasksResource(TasksDAO tasksDao) {
        this.tasksDao = tasksDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasks() {
        return tasksDao.getAllTasks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskById(@PathParam("id") int id) {
        ImmutableTask task = tasksDao.getTaskById(id);
        return Response
                .accepted(task)
                .build();
    }
}
