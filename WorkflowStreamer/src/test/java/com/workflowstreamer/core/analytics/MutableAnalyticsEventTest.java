package com.workflowstreamer.core.analytics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MutableAnalyticsEventTest {
    private static final String EVENT_NAME = "event-name";
    private static final String EVENT_TYPE = "event-type";
    private static final Date DATE = new Date();
    private static final int PROJECT_ID = 123;
    private static final int TASK_ID = 1560;
    private static final int USER_ID = 152013;
    private static final int TEAM_ID = 15;
    private static final ImmutableAnalyticsEvent IMMUTABLE_ANALYTICS_EVENT = ImmutableAnalyticsEvent.builder()
            .eventName(EVENT_NAME)
            .eventType(EVENT_TYPE)
            .time(DATE)
            .projectId(PROJECT_ID)
            .userId(USER_ID)
            .taskId(TASK_ID)
            .teamId(TEAM_ID)
            .build();
    private static final ImmutableAnalyticsEvent INCOMPLETE_IMMUTABLE_ANALYTICS_EVENT = ImmutableAnalyticsEvent.builder()
            .eventName(EVENT_NAME)
            .eventType(EVENT_TYPE)
            .time(DATE)
            .build();

    @Test
    public void itMakesAMutableEventFromAnImmutable() {
        final MutableAnalyticsEvent EXPECTED_MUTABLE_EVENT = new MutableAnalyticsEvent();
        EXPECTED_MUTABLE_EVENT.setEventName(EVENT_NAME);
        EXPECTED_MUTABLE_EVENT.setEventType(EVENT_TYPE);
        EXPECTED_MUTABLE_EVENT.setTime(DATE);
        EXPECTED_MUTABLE_EVENT.setProjectId(PROJECT_ID);
        EXPECTED_MUTABLE_EVENT.setUserId(USER_ID);
        EXPECTED_MUTABLE_EVENT.setTaskId(TASK_ID);
        EXPECTED_MUTABLE_EVENT.setTeamId(TEAM_ID);
        MutableAnalyticsEvent result = new MutableAnalyticsEvent(IMMUTABLE_ANALYTICS_EVENT);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(EXPECTED_MUTABLE_EVENT);
    }

    @Test
    public void itMakesAMutableEventFromAnIncompleteImmutable() {
        final MutableAnalyticsEvent EXPECTED_MUTABLE_EVENT = new MutableAnalyticsEvent();
        EXPECTED_MUTABLE_EVENT.setEventName(EVENT_NAME);
        EXPECTED_MUTABLE_EVENT.setEventType(EVENT_TYPE);
        EXPECTED_MUTABLE_EVENT.setTime(DATE);
        MutableAnalyticsEvent result = new MutableAnalyticsEvent(INCOMPLETE_IMMUTABLE_ANALYTICS_EVENT);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(EXPECTED_MUTABLE_EVENT);
    }
}