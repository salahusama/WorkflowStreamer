package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableNewProject;
import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.manager.ProjectsManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/projects")
public class ProjectsResource {
    private ProjectsManager projectsManager;

    public ProjectsResource(ProjectsManager projectsManager) {
        this.projectsManager = projectsManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableProject> getProjectsByUser(@QueryParam("userId") int userId) {
        return projectsManager.getUserCreatedProjects(userId);
    }

    @PUT
    @Path("/project")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertProject(ImmutableNewProject newProject) {
        // TODO: get userId from auth
        return projectsManager.insertProject(newProject);
    }
}
