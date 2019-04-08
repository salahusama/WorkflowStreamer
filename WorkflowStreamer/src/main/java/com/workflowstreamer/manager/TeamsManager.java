package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserRole;
import com.workflowstreamer.dao.TeamsDAO;
import com.workflowstreamer.dao.UsersDAO;

import javax.ws.rs.core.Response;
import java.util.Set;

public class TeamsManager {
    private final TeamsDAO teamsDAO;
    private final UsersDAO usersDAO;
    private final AnalyticsClient analyticsClient;

    public TeamsManager(TeamsDAO teamsDAO, UsersDAO usersDAO, AnalyticsClient analyticsClient) {
        this.teamsDAO = teamsDAO;
        this.usersDAO = usersDAO;
        this.analyticsClient = analyticsClient;
    }

    public int getTeamIdByTaskId(int taskId) {
        return teamsDAO.getTeamIdByTaskId(taskId);
    }

    public Set<ImmutableTeam> getUserTeams(int userId) {
        return teamsDAO.getTeamsByUser(userId);
    }

    public ImmutableTeam insertTeam(ImmutableNewTeam newTeam) {
        int teamId = teamsDAO.insertTeam(newTeam);
        teamsDAO.addUserToTeam(teamId, newTeam.getUserId(),1);
        return teamsDAO.getTeamById(teamId);
    }

    public Set<ImmutableUserRole> getTeamMembers(int teamId) {
        return teamsDAO.getTeamMembers(teamId);
    }

    public Response addUserToTeam(int teamId, String userEmail) {
        Response.ResponseBuilder response;
        try {
            ImmutableUser user = usersDAO.getUserByEmail(userEmail);
            teamsDAO.addUserToTeam(teamId, user.getUserId(), 2);
            ImmutableUserRole userRole = teamsDAO.getTeamMemberByTeamAndUserId(teamId, user.getUserId());
            response = Response.ok(userRole);
        } catch (Exception e) {
            response = Response.notModified();
        }
        return response.build();
    }
}
