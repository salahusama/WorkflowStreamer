package com.workflowstreamer.manager;

import com.workflowstreamer.core.ImmutableNewProject;
import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.data.dao.ProjectsDAO;

import javax.ws.rs.core.Response;
import java.util.Set;

public class ProjectsManager {
    private final ProjectsDAO projectsDao;

    public ProjectsManager(ProjectsDAO projectsDao) {
        this.projectsDao = projectsDao;
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
        return Response.ok(insertedProject).build();
    }
}
