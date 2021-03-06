package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.*;
import com.workflowstreamer.dao.StagesDAO;
import com.workflowstreamer.dao.UsersDAO;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;

import javax.ws.rs.core.Response;
import java.util.Set;

public class UsersManager {
    private static final String DEFAULT_PROJECT_NAME = "Default";
    private static final String DEFAULT_PROJECT_DESCRIPTION = "This is the default project for you.";
    private static final String DEFAULT_TEAM_NAME = "Default Team";
    private static final String DEFAULT_TEAM_DESCRIPTION = "Every user has a default team they belong to.";

    private final UsersDAO usersDao;
    private final StagesDAO stagesDao;
    private final AnalyticsClient analyticsClient;
    private final ProjectsManager projectsManager;
    private final TeamsManager teamsManager;

    public UsersManager(UsersDAO usersDao, StagesDAO stagesDao, AnalyticsClient analyticsClient, ProjectsManager projectsManager, TeamsManager teamsManager) {
        this.usersDao = usersDao;
        this.stagesDao = stagesDao;
        this.analyticsClient = analyticsClient;
        this.projectsManager = projectsManager;
        this.teamsManager = teamsManager;
    }

    public ImmutableUser getUserById(int id) {
        return usersDao.getUserById(id).withTeams(teamsManager.getUserTeams(id));
    }

    public Set<ImmutableStage> getStages() {
        return stagesDao.getStages();
    }

    public Response login(ImmutableLoginData loginData) {
        Response.ResponseBuilder response;
        ImmutableUser user = usersDao.getUserByUsername(loginData.getUsername());

        if (user != null && user.getPassword().equals(loginData.getPassword())) {
            response = Response.ok(user.withTeams(teamsManager.getUserTeams(user.getUserId())));
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
            response = Response.ok(insertedUser.withTeams(teamsManager.getUserTeams(userId)));
            addDefaults(insertedUser.getUserId());
        } catch (UnableToExecuteStatementException e) {
            response = Response.status(Response.Status.FORBIDDEN);
        }
        return response.build();
    }

    private void addDefaults(int userId) {
        // Add default team
        ImmutableTeam defaultTeam = teamsManager.insertTeam(ImmutableNewTeam.builder()
                .name(DEFAULT_TEAM_NAME)
                .description(DEFAULT_TEAM_DESCRIPTION)
                .userId(userId)
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
    }
}
