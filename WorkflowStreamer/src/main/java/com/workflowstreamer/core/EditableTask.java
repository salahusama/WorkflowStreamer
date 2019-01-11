package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

import java.util.Optional;

@Immutable
@JsonDeserialize
public interface EditableTask {
    int getTaskId();
    Optional<Integer> getProjectId();
    Optional<String> getStage();
    Optional<String> getTitle();
    Optional<String> getDescription();
}
