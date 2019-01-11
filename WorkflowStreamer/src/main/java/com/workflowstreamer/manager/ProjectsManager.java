package com.workflowstreamer.manager;

import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.dao.ProjectsDAO;

import java.util.Set;

public class ProjectsManager {
    private final ProjectsDAO projectsDao;

    public ProjectsManager(ProjectsDAO projectsDao) {
        this.projectsDao = projectsDao;
    }

    public Set<ImmutableProject> getUserCreatedProjects(int userId) {
        return projectsDao.getProjectsByCreator(userId);
    }
}
