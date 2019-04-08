package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@JsonDeserialize
public interface Comment extends BasicComment {
    int getCommentId();
    Optional<String> getUsername();
}