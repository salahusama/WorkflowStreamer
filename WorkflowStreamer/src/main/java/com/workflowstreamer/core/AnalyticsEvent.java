package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

import java.util.Date;
import java.util.Optional;

@Immutable
@JsonSerialize
@JsonDeserialize
public interface AnalyticsEvent {
    Optional<String> getId();

    String getEventName();
    String getEventType();
    Date getTime();

    Optional<Integer> getProjectId();
    Optional<Integer> getTeamId();
    Optional<Integer> getUserId();

    Optional<Integer> getTaskId();
}
