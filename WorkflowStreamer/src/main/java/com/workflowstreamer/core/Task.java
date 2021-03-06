package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.workflowstreamer.core.enums.Priority;
import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Immutable
@JsonDeserialize
public interface Task {
    int getTaskId();
    int getProjectId();
    int getCreatorId();
    String getStage();
    String getTitle();
    Optional<String> getDescription();
    Timestamp getCreatedAt();
    Optional<Priority> getPriority();
    Optional<Integer> getEstimatedWork();
    Optional<Date> getDueDate();
    Optional<Boolean> getIsRecommended();
}
