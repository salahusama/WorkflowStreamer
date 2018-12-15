package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
@JsonDeserialize
public interface NewTask {
    int getCreatorId();
    String getTitle();
    String getDescription();
}