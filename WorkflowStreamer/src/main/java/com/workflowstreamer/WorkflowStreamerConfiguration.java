package com.workflowstreamer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class WorkflowStreamerConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    private String analyticsDomain;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getAnalyticsDomain() {
        return analyticsDomain;
    }
}
