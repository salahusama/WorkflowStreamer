package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.core.ImmutableUserRole;
import com.workflowstreamer.dao.TeamsDAO;

import java.util.Set;

public class TeamsManager {
    private final TeamsDAO teamsDAO;
    private final AnalyticsClient analyticsClient;

    public TeamsManager(TeamsDAO teamsDAO, AnalyticsClient analyticsClient) {
        this.teamsDAO = teamsDAO;
        this.analyticsClient = analyticsClient;
    }

    public int getTeamIdByTaskId(int taskId) {
        return teamsDAO.getTeamIdByTaskId(taskId);
    }

    public void addUserToTeam(int userId, int teamId) {
        teamsDAO.addUserToTeam(userId, teamId);
    }

    public Set<ImmutableTeam> getUserTeams(int userId) {
        return teamsDAO.getTeamsByUser(userId);
    }

    public ImmutableTeam insertTeam(ImmutableNewTeam newTeam) {
        int teamId = teamsDAO.insertTeam(newTeam);
        addUserToTeam(newTeam.getUserId(), teamId);
        return teamsDAO.getTeamById(teamId);
    }

    public Set<ImmutableUserRole> getTeamMembers(int teamId) {
        return teamsDAO.getTeamMembers(teamId);
    }
}
