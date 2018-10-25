package com.workflowstreamer;

import com.workflowstreamer.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class WorkflowStreamerApplication extends Application<WorkflowStreamerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WorkflowStreamerApplication().run(args);
    }

    @Override
    public String getName() {
        return "WorkflowStreamer";
    }

    @Override
    public void initialize(final Bootstrap<WorkflowStreamerConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final WorkflowStreamerConfiguration configuration, final Environment environment) {
        environment.jersey().register(new HelloWorldResource());
    }

}
