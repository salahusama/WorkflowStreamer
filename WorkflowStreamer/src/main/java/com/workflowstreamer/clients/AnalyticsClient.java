package com.workflowstreamer.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workflowstreamer.core.ImmutableAnalyticsEvent;
import okhttp3.*;

import java.io.IOException;

public class AnalyticsClient {
    private static final String url = "http://localhost:5000/events/event";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

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
}
