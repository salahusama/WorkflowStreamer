package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
@JsonDeserialize
public abstract class Task {
    abstract int getTaskId();
    abstract int getProjectId();
    abstract int getCreatorId();
    abstract String getStage();
    abstract String getTitle();
    abstract String getDescription();
    abstract Timestamp getCreatedAt();
}