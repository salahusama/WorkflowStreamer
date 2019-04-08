package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.core.ImmutableUserRole;
import com.workflowstreamer.manager.TeamsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/teams")
public class TeamsResource {
    private TeamsManager teamsManager;

    public TeamsResource(TeamsManager teamsManager) {
        this.teamsManager = teamsManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableTeam> getTeamsByUser(@QueryParam("userId") int userId) {
        return teamsManager.getUserTeams(userId);
    }

    @PUT
    @Path("/team")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTeam(ImmutableNewTeam newTeam) {
        return Response.ok(teamsManager.insertTeam(newTeam)).build();
    }

    @GET
    @Path("/team/{teamId}/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableUserRole> getTeamMembers(@PathParam("teamId") int teamId) {
        return teamsManager.getTeamMembers(teamId);
    }
}
