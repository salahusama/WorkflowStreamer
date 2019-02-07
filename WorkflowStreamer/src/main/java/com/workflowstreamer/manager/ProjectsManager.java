package com.workflowstreamer.manager;

import com.workflowstreamer.clients.AnalyticsClient;
import com.workflowstreamer.core.ImmutableNewProject;
import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.dao.ProjectsDAO;

import javax.ws.rs.core.Response;
import java.util.Set;

public class ProjectsManager {
    private final ProjectsDAO projectsDao;
    private final AnalyticsClient analyticsClient;

    public ProjectsManager(ProjectsDAO projectsDao, AnalyticsClient analyticsClient) {
        this.projectsDao = projectsDao;
        this.analyticsClient = analyticsClient;
    }

    public Set<ImmutableProject> getUserCreatedProjects(int userId) {
        return projectsDao.getProjectsByCreator(userId);
    }

    public Response insertProject(ImmutableNewProject newProject) {
        int generatedId = projectsDao.insertProject(
                newProject.getCreatorId(),
                newProject.getName(),
                newProject.getDescription().orElse(null)
        );
        ImmutableProject insertedProject = projectsDao.getProjectById(generatedId);
        analyticsClient.fireEvent(AnalyticsClient.AnalyticsEventBuilderFrom(insertedProject, AnalyticsClient.Events.ProjectInteraction.Types.CREATED).build());
        return Response.ok(insertedProject).build();
    }
}
