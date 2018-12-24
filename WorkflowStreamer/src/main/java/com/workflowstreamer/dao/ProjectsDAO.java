package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.dao.mapper.ProjectMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface ProjectsDAO {
    @Mapper(ProjectMapper.class)
    @SqlQuery("SELECT project_id, creator_id, name, description from projects where creator_id = :userId")
    Set<ImmutableProject> getProjectsByCreator(@Bind("userId") int userId);
}