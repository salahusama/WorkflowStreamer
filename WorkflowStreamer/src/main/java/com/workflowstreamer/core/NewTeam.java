package com.workflowstreamer.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonDeserialize
public interface NewTeam extends BasicTeam {
    int getUserId();
}