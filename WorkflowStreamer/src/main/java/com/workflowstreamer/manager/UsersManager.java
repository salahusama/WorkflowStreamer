package com.workflowstreamer.manager;

import com.google.common.collect.ImmutableList;
import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.*;
import com.workflowstreamer.dao.UsersDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.util.Set;

public class UsersManager {
    private static final ImmutableList<String> DEFAULT_STAGES = ImmutableList.of("Back Log", "In Progress", "Done");
    private static final String DEFAULT_PROJECT_NAME = "Default";
    private static final String DEFAULT_PROJECT_DESCRIPTION = "This is the default project for you.";
    private static final String DEFAULT_TEAM_NAME = "Default Team";
    private static final String DEFAULT_TEAM_DESCRIPTION = "Every user has a default team they belong to.";

    private final UsersDAO usersDao;
    private final AnalyticsClient analyticsClient;
    private final ProjectsManager projectsManager;
    private final TeamsManager teamsManager;

    public UsersManager(UsersDAO usersDao, AnalyticsClient analyticsClient, ProjectsManager projectsManager, TeamsManager teamsManager) {
        this.usersDao = usersDao;
        this.analyticsClient = analyticsClient;
        this.projectsManager = projectsManager;
        this.teamsManager = teamsManager;
    }

    public ImmutableUser getUserById(int id) {
        return usersDao.getUserById(id);
    }

    public Set<ImmutableUserStage> getUserStagesByUserId(int userId) {
        return usersDao.getUserStagesByUserId(userId);
    }

    public Response login(ImmutableLoginData loginData) {
        Response.ResponseBuilder response;
        ImmutableUser user = usersDao.getUserByUsername(loginData.getUsername());

        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            response = Response.ok(user);
        } else {
            response = Response.status(Response.Status.NOT_FOUND);
        }

        return response.build();
    }

    public Response addNewUser(ImmutableNewUser newUser) {
        Response.ResponseBuilder response;

        try {
            int userId = usersDao.insertUser(newUser.getEmail(), newUser.getUsername(), newUser.getPassword());
            ImmutableUser insertedUser = usersDao.getUserById(userId);
            response = Response.ok(insertedUser);
            addDefaults(insertedUser.getUserId());
        } catch (UnableToExecuteStatementException e) {
            response = Response.status(Response.Status.FORBIDDEN);
        }
        return response.build();
    }

    public Response addNewUserStage(ImmutableUserStage stage) {
        Response.ResponseBuilder response;

        try {
            usersDao.insertUserStage(stage);
            response = Response.ok();
        } catch (UnableToExecuteStatementException e) {
            response = Response.status(Response.Status.CONFLICT);
        }
        return response.build();
    }

    private void addDefaults(int userId) {
        // Add default team
        ImmutableTeam defaultTeam = teamsManager.insertTeam(ImmutableNewTeam.builder()
                .name(DEFAULT_TEAM_NAME)
                .description(DEFAULT_TEAM_DESCRIPTION)
                .build()
        );

        // Add default project
        projectsManager.insertProject(ImmutableNewProject.builder()
                .creatorId(userId)
                .teamId(defaultTeam.getTeamId())
                .name(DEFAULT_PROJECT_NAME)
                .description(DEFAULT_PROJECT_DESCRIPTION)
                .build()
        );

        // Add default stages
        addDefaultStages(userId);
    }

    private void addDefaultStages(int userId) {
        try {
            int viewOrder = 1;
            for (String stage : DEFAULT_STAGES) {
                usersDao.insertUserStage(ImmutableUserStage.builder()
                        .userId(userId)
                        .stage(stage)
                        .viewOrder(viewOrder++)
                        .build()
                );
            }
        } catch (UnableToExecuteStatementException e) {
            e.printStackTrace();
        }
    }
}
