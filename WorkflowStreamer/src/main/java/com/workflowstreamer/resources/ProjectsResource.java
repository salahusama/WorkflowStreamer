package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.manager.ProjectsManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
}
