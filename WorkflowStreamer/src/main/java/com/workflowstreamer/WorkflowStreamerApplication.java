package com.workflowstreamer;

import com.workflowstreamer.dao.TasksDAO;
import com.workflowstreamer.dao.UsersDAO;
import com.workflowstreamer.resources.TasksResource;
import com.workflowstreamer.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
    public void run(final WorkflowStreamerConfiguration config, final Environment environment) throws ClassNotFoundException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");

        final UsersDAO usersDao = jdbi.onDemand(UsersDAO.class);
        final TasksDAO tasksDao = jdbi.onDemand(TasksDAO.class);

        // I could pass a manager here instead of a DAO
        environment.jersey().register(new TasksResource(tasksDao));
        environment.jersey().register(new UsersResource(usersDao));
    }
}
