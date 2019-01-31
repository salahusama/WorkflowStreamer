package com.workflowstreamer.data.mongo;

import com.google.common.collect.ImmutableList;
import com.workflowstreamer.core.analytics.ImmutableAnalyticsEvent;
import com.workflowstreamer.core.analytics.MutableAnalyticsEvent;
import net.vz.mongodb.jackson.DBCursor;

import java.util.ArrayList;

public class AnalyticsEventMapper {
    public static ImmutableList<ImmutableAnalyticsEvent> mapToList(DBCursor<MutableAnalyticsEvent> dbCursor) {
        ArrayList<ImmutableAnalyticsEvent> arrayList = new ArrayList<>();
        while (dbCursor.hasNext()) {
            MutableAnalyticsEvent mutableAnalyticsEvent = dbCursor.next();
            arrayList.add(ImmutableAnalyticsEvent.builder()
                    .id(mutableAnalyticsEvent.get_id())
                    .eventName(mutableAnalyticsEvent.getEventName())
                    .eventType(mutableAnalyticsEvent.getEventType())
                    .time(mutableAnalyticsEvent.getTime())
                    .projectId(mutableAnalyticsEvent.getProjectId())
                    .teamId(mutableAnalyticsEvent.getTeamId())
                    .userId(mutableAnalyticsEvent.getUserId())
                    .taskId(mutableAnalyticsEvent.getTaskId())
                    .build()
            );
        }
        return ImmutableList.copyOf(arrayList);
    }
}