package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize
public interface UserRole {
    int getUserId();
    String getEmail();
    String getUsername();
    String getRole();
    String getRoleDescription();
}
