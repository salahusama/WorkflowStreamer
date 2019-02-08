package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableProject;
import com.workflowstreamer.dao.mapper.ProjectMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface ProjectsDAO {
    @Mapper(ProjectMapper.class)
    @SqlQuery("SELECT * FROM projects WHERE project_id = :id")
    ImmutableProject getProjectById(@Bind("id") int id);

    @Mapper(ProjectMapper.class)
    @SqlQuery("SELECT project_id, creator_id, name, description from projects where creator_id = :userId")
    Set<ImmutableProject> getProjectsByCreator(@Bind("userId") int userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO projects (creator_id, name, description) VALUES (:creatorId, :name, :desc)")
    int insertProject(@Bind("creatorId") int creatorId, @Bind("name") String name, @Bind("desc") String description);
}