package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.ImmutableEditableTask;
import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.stream.Collectors;

public class TasksManager {
    private final TasksDAO tasksDao;
    private final AnalyticsClient analyticsClient;

    public TasksManager(TasksDAO tasksDao, AnalyticsClient analyticsClient) {
        this.tasksDao = tasksDao;
        this.analyticsClient = analyticsClient;
    }

    public Set<ImmutableTask> getTasksByProjectId(int projectId) {
        return tasksDao.getTasksByProjectId(projectId);
    }

    public Set<ImmutableTask> getTasksCreatedByUser(int userId) {
        Set<ImmutableTask> userTasks = tasksDao.getTasksByUser(userId);
        return userTasks.stream()
                .map(task -> task.withIsRecommended(checkRecommendation(task)))
                .collect(Collectors.toSet());
    }

    public ImmutableTask getTaskById(int taskId) {
        ImmutableTask task = tasksDao.getTaskById(taskId);
        return task.withIsRecommended(checkRecommendation(task));
    }

    public Response insertTask(ImmutableNewTask newTask) {
        int generatedId = tasksDao.insertTask(newTask);
        ImmutableTask insertedTask = getTaskById(generatedId);
        analyticsClient.trackEvent(AnalyticsClient.AnalyticsEventBuilderFrom(insertedTask, AnalyticsClient.Events.TaskInteraction.Types.CREATED_TASK).build());

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
                    newInfo.getDescription().orElse(currentInfo.getDescription().orElse(null)),
                    newInfo.getPriority().orElse(currentInfo.getPriority().orElse(null)),
                    newInfo.getEstimatedWork().orElse(currentInfo.getEstimatedWork().orElse(null)),
                    newInfo.getDueDate().orElse(currentInfo.getDueDate().orElse(null))
            );

            if (rowsAffected != 1) {
                throw new UnableToExecuteStatementException(new Exception("No rows affected"), null);
            }

            ImmutableTask updatedTask = getTaskById(taskId);
            response = Response.ok(updatedTask);
        } catch (UnableToExecuteStatementException e) {
            response = Response.notModified();
        }

        return response.build();
    }

    private boolean checkRecommendation(ImmutableTask task) {
        return task.getTitle().contains("API");
    }
}
