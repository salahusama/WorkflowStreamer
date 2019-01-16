package com.workflowstreamer.core;

import java.util.Optional;

public interface BasicProject {
    int getCreatorId();
    String getName();
    Optional<String> getDescription();
}
