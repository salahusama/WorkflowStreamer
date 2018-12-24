package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @Path("/project/{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasksByProjectId(@PathParam("projectId") int projectId) {
        return tasksDao.getTasksByProjectId(projectId);
    }

    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTask> getTasksForUserId(@PathParam("userId") int userId) {
        return tasksDao.getTasksByUser(userId);
    }

    @GET
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableTask getTaskById(@PathParam("id") int id) {
        return tasksDao.getTaskById(id);
    }

    @PUT
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTask(ImmutableNewTask newTask) {
        // TODO: get userId from auth
        int generatedId = tasksDao.insertTask(
                newTask.getProjectId(),
                newTask.getCreatorId(),
                newTask.getStage(),
                newTask.getTitle(),
                newTask.getDescription(),
                newTask.getCreatorId(),
                Timestamp.valueOf(LocalDateTime.now())
        );
        ImmutableTask insertedTask = tasksDao.getTaskById(generatedId);
        return Response.ok(insertedTask).build();
    }
}
