package com.workflowstreamer.core;

import java.util.Optional;

public interface BasicProject {
    int getCreatorId();
    int getTeamId();
    String getName();
    Optional<String> getDescription();
}
