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
    @JsonProperty
    private String mongoDB;

    @Valid
    @NotNull
    @JsonProperty
    private String mongoHost;

    @Valid
    @NotNull
    @JsonProperty
    private Integer mongoPort;

    @Valid
    @NotNull
    @JsonProperty
    private String mongoUser;

    @Valid
    @NotNull
    @JsonProperty
    private String mongoPassword;

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public String getMongoDB() {
        return mongoDB;
    }

    public String getMongoUser() {
        return mongoUser;
    }

    public String getMongoPassword() {
        return mongoPassword;
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public Integer getMongoPort() {
        return mongoPort;
    }
}
