package com.workflowstreamer.resources;

import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.dao.ProjectsDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("/projects")
public class ProjectsResource {
    private ProjectsDAO projectsDAO;

    public ProjectsResource(ProjectsDAO projectsDAO) {
        this.projectsDAO = projectsDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ImmutableProject> getProjectsByUser(@QueryParam("userId") int userId) {
        return projectsDAO.getProjectsByCreator(userId);
    }
}
