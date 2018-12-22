package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
@JsonDeserialize
public interface Task {
    int getTaskId();
    int getCreatorId();
    String getTitle();
    String getDescription();
    Timestamp getCreatedAt();
}