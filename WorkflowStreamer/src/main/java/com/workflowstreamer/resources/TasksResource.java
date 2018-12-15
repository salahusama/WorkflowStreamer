package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImmutableTask getTaskById(@PathParam("id") int id) {
        return tasksDao.getTaskById(id);
    }

    @PUT
    @Path("/task")
    @Consumes(MediaType.APPLICATION_JSON)
    public void insertTask(ImmutableNewTask newTask) {
        // TODO: get userId from auth
        tasksDao.insertTask(newTask.getTitle(), newTask.getDescription(), newTask.getCreatorId(), Timestamp.valueOf(LocalDateTime.now()));
    }
}
