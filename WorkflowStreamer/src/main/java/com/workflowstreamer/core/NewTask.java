package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize
public interface NewTask {
    int getProjectId();
    int getCreatorId();
    String getStage();
    String getTitle();
    String getDescription();
}