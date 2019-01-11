package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.manager.TasksManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/tasks")
public class TasksResource {
    private TasksManager tasksManager;

    public TasksResource(TasksManager tasksManager) {
        this.tasksManager = tasksManager;
    }

    @GET
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasksByProjectId(@PathParam("projectId") int projectId) {
        return tasksManager.getTasksByProjectId(projectId);
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasksForUserId(@PathParam("userId") int userId) {
        return tasksManager.getTasksCreatedByUser(userId);
    }

    @GET
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableTask getTaskById(@PathParam("id") int id) {
        return tasksManager.getTaskById(id);
    }

    @PUT
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTask(ImmutableNewTask newTask) {
        // TODO: get userId from auth
        return tasksManager.insertTask(newTask);
    }
}
