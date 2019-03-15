package com.workflowstreamer.core;

import org.immutables.value.Value.Immutable;

import java.util.Optional;
import java.util.Set;

@Immutable
public interface User {
    int getUserId();
    String getEmail();
    String getUsername();
    String getPassword();
    Optional<Set<ImmutableTeam>> getTeams();
}
