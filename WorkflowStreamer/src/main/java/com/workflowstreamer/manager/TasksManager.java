package com.workflowstreamer.manager;

import com.google.common.collect.ImmutableSet;
import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.*;
import com.workflowstreamer.dao.CommentsDAO;
import com.workflowstreamer.dao.TasksDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TasksManager {
    private final TasksDAO tasksDao;
    private final CommentsDAO commentsDAO;
    private final TeamsManager teamsManager;
    private final AnalyticsClient analyticsClient;

    public TasksManager(TeamsManager teamsManager, TasksDAO tasksDao, CommentsDAO commentsDAO, AnalyticsClient analyticsClient) {
        this.tasksDao = tasksDao;
        this.commentsDAO = commentsDAO;
        this.teamsManager = teamsManager;
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

    public Set<ImmutableTask> getTasksByUserTeams(int userId) {
        Set<ImmutableTeam> userTeams = teamsManager.getUserTeams(userId);

        // Each user must have be in at least 1 team (default team)
        // This is a measure in case sth goes wrong
        if (userTeams.size() == 0) {
            return ImmutableSet.of();
        }

        List<Integer> teamIds = userTeams.stream().map(ImmutableTeam::getTeamId).collect(Collectors.toList());
        Set<ImmutableTask> teamTasks = tasksDao.getTasksByTeamIds(teamIds);

        return teamTasks.stream()
                .map(task -> task.withIsRecommended(checkRecommendation(task)))
                .collect(Collectors.toSet());
    }

    @Deprecated
    public Set<ImmutableTask> getTasksByUserTeamsBeta(int userId) {
        Set<ImmutableTeam> userTeams = teamsManager.getUserTeams(userId);

        // Each user must have be in at least 1 team (default team)
        // This is a measure in case sth goes wrong
        if (userTeams.size() == 0) {
            return ImmutableSet.of();
        }

        List<Integer> teamIds = userTeams.stream().map(ImmutableTeam::getTeamId).collect(Collectors.toList());
        Set<ImmutableTask> teamTasks = tasksDao.getTasksByTeamIds(teamIds);

        return RecommendationManager.addRecommendationToTasks(teamTasks);
    }

    public ImmutableTask getTaskById(int taskId) {
        ImmutableTask task = tasksDao.getTaskById(taskId);
        return task.withIsRecommended(checkRecommendation(task));
    }

    public Response insertTask(ImmutableNewTask newTask) {
        int generatedId = tasksDao.insertTask(newTask);
        ImmutableTask insertedTask = getTaskById(generatedId);
        analyticsClient.fireEvent(AnalyticsClient.AnalyticsEventBuilderFrom(insertedTask, AnalyticsClient.Events.TaskInteraction.Types.CREATED).build());
        return Response.ok(insertedTask).build();
    }

    public Response updateTask(ImmutableEditableTask newInfo) {
        int taskId = newInfo.getTaskId();
        ImmutableTask currentInfo = getTaskById(taskId);
        Response.ResponseBuilder response;
        ImmutableTask oldTask = getTaskById(taskId);

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
            if (!newInfo.getDescription().isPresent()) {
                // We do not care about updated descriptions
                analyticsClient.fireEvent(AnalyticsClient.AnalyticsEventBuilderFrom(teamsManager.getTeamIdByTaskId(taskId), updatedTask, oldTask).build());
            }
            response = Response.ok(updatedTask);
        } catch (UnableToExecuteStatementException e) {
            response = Response.notModified();
        }

        return response.build();
    }

    public Response getTaskComments(int taskId) {
        Set<ImmutableComment> comments = commentsDAO.getCommentsForTask(taskId);
        return Response.ok(comments).build();
    }

    public Response addComment(ImmutableNewComment newComment) {
        int generatedId = commentsDAO.addComment(newComment);
        ImmutableComment addedComment = commentsDAO.getCommentById(generatedId);
        // TODO: Add analytics tracking
        return Response.ok(addedComment).build();
    }

    private boolean checkRecommendation(ImmutableTask task) {
        return task.getTitle().contains("API");
    }
}
