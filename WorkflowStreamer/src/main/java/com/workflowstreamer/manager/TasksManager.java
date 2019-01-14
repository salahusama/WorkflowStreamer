package com.workflowstreamer.manager;

import com.workflowstreamer.core.ImmutableEditableTask;
import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public class TasksManager {
    private final TasksDAO tasksDao;

    public TasksManager(TasksDAO tasksDao) {
        this.tasksDao = tasksDao;
    }

    public Set<ImmutableTask> getTasksByProjectId(int projectId) {
        return tasksDao.getTasksByProjectId(projectId);
    }

    public Set<ImmutableTask> getTasksCreatedByUser(int userId) {
        return tasksDao.getTasksByUser(userId);
    }

    public ImmutableTask getTaskById(int taskId) {
        return tasksDao.getTaskById(taskId);
    }

    public Response insertTask(ImmutableNewTask newTask) {
        int generatedId = tasksDao.insertTask(
                newTask.getProjectId(),
                newTask.getCreatorId(),
                newTask.getStage(),
                newTask.getTitle(),
                newTask.getDescription(),
                newTask.getCreatorId(),
                Timestamp.valueOf(LocalDateTime.now()),
                newTask.getPriority().orElse(null),
                newTask.getEstimatedWork().orElse(null),
                newTask.getDueDate().orElse(null)
        );
        ImmutableTask insertedTask = tasksDao.getTaskById(generatedId);
        return Response.ok(insertedTask).build();
    }

    public Response updateTask(ImmutableEditableTask newInfo) {
        int taskId = newInfo.getTaskId();
        ImmutableTask currentInfo = getTaskById(taskId);
        Response.ResponseBuilder response;

        try {
            int rowsAffected = tasksDao.updateTask(
                    taskId,
                    newInfo.getProjectId().orElse(currentInfo.getProjectId()),
                    // TODO: Stage is a foreign key - FE or BE need to ensure its valid
                    newInfo.getStage().orElse(currentInfo.getStage()),
                    newInfo.getTitle().orElse(currentInfo.getTitle()),
                    newInfo.getDescription().orElse(currentInfo.getDescription()),
                    newInfo.getPriority().orElse(currentInfo.getPriority().orElse(null)),
                    newInfo.getEstimatedWork().orElse(currentInfo.getEstimatedWork().orElse(null)),
                    newInfo.getDueDate().orElse(currentInfo.getDueDate().orElse(null))
            );

            if (rowsAffected != 1) {
                throw new UnableToExecuteStatementException("No rows affected");
            }

            ImmutableTask updatedTask = tasksDao.getTaskById(taskId);
            response = Response.ok(updatedTask);
        } catch (UnableToExecuteStatementException e) {
            response = Response.notModified();
        }

        return response.build();
    }
}
