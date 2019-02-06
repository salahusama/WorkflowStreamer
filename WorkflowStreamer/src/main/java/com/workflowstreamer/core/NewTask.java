package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workflowstreamer.core.enums.Priority;
import org.immutables.value.Value.Immutable;

import java.util.Date;
import java.util.Optional;

@Immutable
@JsonDeserialize
public interface NewTask {
    int getProjectId();
    int getCreatorId();
    String getStage();
    String getTitle();
    Optional<String> getDescription();
    Optional<Priority> getPriority();
    Optional<Integer> getEstimatedWork();
    Optional<Date> getDueDate();
}
