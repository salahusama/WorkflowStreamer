package com.workflowstreamer.core.analytics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Date;
import java.util.Optional;

@Value.Immutable
@JsonSerialize(as = ImmutableAnalyticsEvent.class)
@JsonDeserialize(as = ImmutableAnalyticsEvent.class)
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
