package com.workflowstreamer.manager;

import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;

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
                Timestamp.valueOf(LocalDateTime.now())
        );
        ImmutableTask insertedTask = tasksDao.getTaskById(generatedId);
        return Response.ok(insertedTask).build();
    }
}
