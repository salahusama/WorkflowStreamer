package com.workflowstreamer.core.analytics;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableAnalyticsEvent.class)
@JsonDeserialize(as = ImmutableAnalyticsEvent.class)
public interface AnalyticsEvent extends BasicAnalyticsEvent {
    String getId();
}
