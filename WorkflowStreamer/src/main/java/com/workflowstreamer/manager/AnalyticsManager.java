package com.workflowstreamer.manager;

import com.google.common.collect.ImmutableList;
import com.workflowstreamer.core.analytics.ImmutableAnalyticsEvent;
import com.workflowstreamer.core.analytics.MutableAnalyticsEvent;
import com.workflowstreamer.data.mongo.AnalyticsEventMapper;
import net.vz.mongodb.jackson.JacksonDBCollection;

public class AnalyticsManager {
    private final JacksonDBCollection<MutableAnalyticsEvent, String> eventsCollection;

    public AnalyticsManager(JacksonDBCollection<MutableAnalyticsEvent, String> eventsCollection) {
        this.eventsCollection = eventsCollection;
    }

    public ImmutableList<ImmutableAnalyticsEvent> getAnalyticsEvents() {
        return AnalyticsEventMapper.mapToList(eventsCollection.find());
    }

    public void fireEvent(ImmutableAnalyticsEvent analyticsEvent) {
        eventsCollection.insert(new MutableAnalyticsEvent(analyticsEvent));
    }
}
