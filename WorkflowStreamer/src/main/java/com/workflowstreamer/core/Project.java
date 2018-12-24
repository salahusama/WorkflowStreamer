package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize
public interface Project {
    int getProjectId();
    int getCreatorId();
    String getName();
    String getDescription();
}