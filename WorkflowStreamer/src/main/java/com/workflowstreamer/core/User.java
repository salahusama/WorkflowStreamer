package com.workflowstreamer.core;

import org.immutables.value.Value.Immutable;

import java.sql.Timestamp;

@Immutable
public interface User {
    int getUserId();
    String getUsername();
    String getPassword();
}