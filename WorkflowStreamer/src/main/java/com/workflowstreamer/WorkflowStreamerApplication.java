package com.workflowstreamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.dao.*;
import com.workflowstreamer.manager.ProjectsManager;
import com.workflowstreamer.manager.TasksManager;
import com.workflowstreamer.manager.TeamsManager;
import com.workflowstreamer.manager.UsersManager;
import com.workflowstreamer.resources.ProjectsResource;
import com.workflowstreamer.resources.TasksResource;
import com.workflowstreamer.resources.TeamsResource;
import com.workflowstreamer.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import okhttp3.OkHttpClient;
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
        final StagesDAO stagesDAO = jdbi.onDemand(StagesDAO.class);
        final TasksDAO tasksDao = jdbi.onDemand(TasksDAO.class);
        final CommentsDAO commentsDAO = jdbi.onDemand(CommentsDAO.class);
        final ProjectsDAO projectsDAO = jdbi.onDemand(ProjectsDAO.class);
        final TeamsDAO teamsDAO = jdbi.onDemand(TeamsDAO.class);

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        final OkHttpClient httpClient = new OkHttpClient();
        final AnalyticsClient analyticsClient = new AnalyticsClient(config.getAnalyticsDomain(), httpClient, objectMapper);

        final TeamsManager teamsManager = new TeamsManager(teamsDAO, usersDao, analyticsClient);
        final TasksManager tasksManager = new TasksManager(teamsManager, tasksDao, usersDao, commentsDAO, analyticsClient);
        final ProjectsManager projectsManager = new ProjectsManager(projectsDAO, analyticsClient);
        final UsersManager usersManager = new UsersManager(usersDao, stagesDAO, analyticsClient, projectsManager, teamsManager);

        environment.jersey().register(new TasksResource(tasksManager));
        environment.jersey().register(new UsersResource(usersManager));
        environment.jersey().register(new ProjectsResource(projectsManager));
        environment.jersey().register(new TeamsResource(teamsManager));
        configureCors(environment);
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
