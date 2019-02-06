package com.workflowstreamer.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflowstreamer.core.ImmutableAnalyticsEvent;
import com.workflowstreamer.core.ImmutableTask;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AnalyticsClient {
    private static final String url = "http://localhost:5000/events/event";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public interface Events {
        interface TaskInteraction {
            String NAME = "task-interaction";
            interface Types {
                String CREATED_TASK = "created-task";
            }
        }
    }

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AnalyticsClient(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public void trackEvent(ImmutableAnalyticsEvent event) {
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
}
