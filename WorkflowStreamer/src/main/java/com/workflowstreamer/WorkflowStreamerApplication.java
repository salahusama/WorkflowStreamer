package com.workflowstreamer;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.workflowstreamer.core.analytics.MutableAnalyticsEvent;
import com.workflowstreamer.data.dao.ProjectsDAO;
import com.workflowstreamer.data.dao.TasksDAO;
import com.workflowstreamer.data.dao.UsersDAO;
import com.workflowstreamer.manager.AnalyticsManager;
import com.workflowstreamer.manager.ProjectsManager;
import com.workflowstreamer.manager.TasksManager;
import com.workflowstreamer.manager.UsersManager;
import com.workflowstreamer.resources.AnalyticsResource;
import com.workflowstreamer.resources.ProjectsResource;
import com.workflowstreamer.resources.TasksResource;
import com.workflowstreamer.resources.UsersResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.vz.mongodb.jackson.JacksonDBCollection;
import org.skife.jdbi.v2.DBI;

import java.net.UnknownHostException;

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
    public void run(final WorkflowStreamerConfiguration config, final Environment environment) throws ClassNotFoundException, UnknownHostException {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "mysql");

        final MongoClient mongoClient = new MongoClient(config.getMongoHost(), config.getMongoPort());
        final DB mongoDB = mongoClient.getDB(config.getMongoDB());

        final JacksonDBCollection<MutableAnalyticsEvent, String> eventsCollection = JacksonDBCollection.wrap(mongoDB.getCollection(WorkflowStreamerConstants.EVENTS_COLLECTION_NAME), MutableAnalyticsEvent.class, String.class);
        final AnalyticsManager analyticsManager = new AnalyticsManager(eventsCollection);

        final UsersDAO usersDao = jdbi.onDemand(UsersDAO.class);
        final TasksDAO tasksDao = jdbi.onDemand(TasksDAO.class);
        final ProjectsDAO projectsDAO = jdbi.onDemand(ProjectsDAO.class);

        final TasksManager tasksManager = new TasksManager(tasksDao);
        final ProjectsManager projectsManager = new ProjectsManager(projectsDAO);
        final UsersManager usersManager = new UsersManager(usersDao, projectsManager);

        environment.jersey().register(new AnalyticsResource(analyticsManager));
        environment.jersey().register(new TasksResource(tasksManager));
        environment.jersey().register(new UsersResource(usersManager));
        environment.jersey().register(new ProjectsResource(projectsManager));
    }
}
