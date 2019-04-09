package com.workflowstreamer.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.workflowstreamer.core.ImmutableAnalyticsEvent;
import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.core.ImmutableTask;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AnalyticsClient {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public interface Events {
        interface TaskInteraction {
            String NAME = "task-interaction";
            interface Types {
                String CREATED = "created";
                String UPDATED = "updated";
            }
        }
        interface ProjectInteraction {
            String NAME = "project-interaction";
            interface Types {
                String CREATED = "created";
            }
        }
    }

    private final String url;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AnalyticsClient(String domain, OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.url = String.format("%s/events/event", domain);
    }

    public void fireEvent(ImmutableAnalyticsEvent event) {
        String eventAsJSONString;
        try {
            eventAsJSONString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        RequestBody body = RequestBody.create(JSON, eventAsJSONString);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try {
            httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImmutableAnalyticsEvent.Builder AnalyticsEventBuilderFrom(ImmutableTask task, String type) {
        return ImmutableAnalyticsEvent.builder()
                .eventName(Events.TaskInteraction.NAME)
                .eventType(type)
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .taskId(task.getTaskId())
                .userId(task.getCreatorId())
                .projectId(task.getProjectId());
    }

    public static ImmutableAnalyticsEvent.Builder AnalyticsEventBuilderFrom(int teamId, ImmutableTask updatedTask, ImmutableTask oldTask) {
        // extraParams contain details of what changed
        ImmutableMap.Builder extraParamsBuilder = ImmutableMap.<String, String>builder();
        // Only add fields that changed
        if (!oldTask.getStage().equals(updatedTask.getStage())) {
            extraParamsBuilder
                    .put("old-stage", oldTask.getStage())
                    .put("new-stage", updatedTask.getStage());
        }
        if (!oldTask.getPriority().equals(updatedTask.getPriority())) {
            extraParamsBuilder
                    .put("old-priority", oldTask.getPriority().isPresent() ? oldTask.getPriority().get().toString() : "")
                    .put("new-priority", updatedTask.getPriority().isPresent() ? updatedTask.getPriority().get().toString() : "");
        }
        if (!oldTask.getDueDate().equals(updatedTask.getDueDate())) {
            extraParamsBuilder
                    .put("old-dueDate", oldTask.getDueDate().isPresent() ? oldTask.getDueDate().get().toString() : "")
                    .put("new-dueDate", updatedTask.getDueDate().isPresent() ? updatedTask.getDueDate().get().toString() : "");
        }
        if (!oldTask.getEstimatedWork().equals(updatedTask.getEstimatedWork())) {
            extraParamsBuilder
                    .put("old-estimatedWork", oldTask.getEstimatedWork().isPresent() ? oldTask.getEstimatedWork().get().toString() : "")
                    .put("new-estimatedWork", updatedTask.getEstimatedWork().isPresent() ? updatedTask.getEstimatedWork().get().toString() : "");
        }

        return ImmutableAnalyticsEvent.builder()
                .eventName(Events.TaskInteraction.NAME)
                .eventType(Events.TaskInteraction.Types.UPDATED)
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .taskId(updatedTask.getTaskId())
                .userId(updatedTask.getCreatorId())
                .projectId(updatedTask.getProjectId())
                .teamId(teamId)
                .extraParams(extraParamsBuilder.build());
    }

    public static ImmutableAnalyticsEvent.Builder AnalyticsEventBuilderFrom(ImmutableProject project, String type) {
        return ImmutableAnalyticsEvent.builder()
                .eventName(Events.ProjectInteraction.NAME)
                .eventType(type)
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .userId(project.getCreatorId())
                .projectId(project.getProjectId())
                .teamId(project.getTeamId());
    }
}
