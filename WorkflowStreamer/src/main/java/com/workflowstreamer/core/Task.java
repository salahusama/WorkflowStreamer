package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
@JsonDeserialize
public interface Task {
    int getTaskId();
    int getProjectId();
    int getCreatorId();
    String getStage();
    String getTitle();
    String getDescription();
    Timestamp getCreatedAt();
}