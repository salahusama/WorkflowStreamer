package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableEditableTask;
import com.workflowstreamer.core.ImmutableNewComment;
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
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableTask getTaskById(@PathParam("id") int id) {
        return tasksManager.getTaskById(id);
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
    @Path("/teams/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasksByUserTeams(@PathParam("userId") int userId) {
        return tasksManager.getTasksByUserTeams(userId);
    }

    @PUT
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTask(ImmutableNewTask newTask) {
        // TODO: get userId from auth
        return tasksManager.insertTask(newTask);
    }

    @POST
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(ImmutableEditableTask task) {
        return tasksManager.updateTask(task);
    }

    @GET
    @Path("/task/{taskId}/comments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommentsForTask(@PathParam("taskId") int taskId) {
        return tasksManager.getTaskComments(taskId);
    }

    @PUT
    @Path("/task/{taskId}/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addComment(ImmutableNewComment newComment) {
        return tasksManager.addComment(newComment);
    }
}
