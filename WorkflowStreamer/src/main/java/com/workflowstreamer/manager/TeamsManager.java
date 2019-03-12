package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.dao.TeamsDAO;

import java.util.Set;

public class TeamsManager {
    private final TeamsDAO teamsDAO;
    private final AnalyticsClient analyticsClient;

    public TeamsManager(TeamsDAO teamsDAO, AnalyticsClient analyticsClient) {
        this.teamsDAO = teamsDAO;
        this.analyticsClient = analyticsClient;
    }

    public Set<ImmutableTeam> getUserTeams(int userId) {
        return teamsDAO.getTeamsByUser(userId);
    }

    public ImmutableTeam insertTeam(ImmutableNewTeam newTeam) {
        int teamId = teamsDAO.insertTeam(newTeam);
        return teamsDAO.getTeamById(teamId);
    }
}
