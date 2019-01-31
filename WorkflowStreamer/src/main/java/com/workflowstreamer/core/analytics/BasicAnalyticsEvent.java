package com.workflowstreamer.core.analytics;

import java.util.Date;
import java.util.Optional;

public interface BasicAnalyticsEvent {
    String getEventName();
    String getEventType();
    Date getTime();

    Optional<Integer> getProjectId();
    Optional<Integer> getTeamId();
    Optional<Integer> getUserId();

    Optional<Integer> getTaskId();
}
