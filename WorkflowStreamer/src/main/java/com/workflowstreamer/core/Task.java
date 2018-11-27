package com.workflowstreamer.core;

import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
public interface Task {
    int getTaskId();
    int getCreatorId();
    String getTitle();
    String getDescription();
    Timestamp getCreatedAt();
}