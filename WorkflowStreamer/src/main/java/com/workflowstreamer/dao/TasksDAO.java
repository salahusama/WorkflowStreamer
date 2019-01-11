package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.mapper.TaskMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.sql.Timestamp;
import java.util.Set;

public interface TasksDAO {
    @Mapper(TaskMapper.class)
    @SqlQuery("select task_id, project_id, creator_id, stage, title, description, created_at from tasks where creator_id = :userId")
    Set<ImmutableTask> getTasksByUser(@Bind("userId") int userId);

    @Mapper(TaskMapper.class)
    @SqlQuery("select task_id, project_id, creator_id, stage, title, description, created_at from tasks where project_id = :projectId")
    Set<ImmutableTask> getTasksByProjectId(@Bind("projectId") int projectId);

    @Mapper(TaskMapper.class)
    @SqlQuery("select task_id, project_id, creator_id, stage, title, description, created_at from tasks where task_id = :id")
    ImmutableTask getTaskById(@Bind("id") int id);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO TASKS (project_id, creator_id, stage, title, description, created_at)" +
               "VALUES (:projectId, :creatorId, :stage, :title, :desc, :createdAt)")
    int insertTask(
            @Bind("projectId") int projectId,
            @Bind("creatorId") int creatorId,
            @Bind("stage") String stage,
            @Bind("title") String title,
            @Bind("desc") String description,
            @Bind("user") int userId,
            @Bind("createdAt") Timestamp createdAt
    );

    @SqlUpdate("UPDATE TASKS " +
               "SET project_id = :projectId, stage = :stage, title = :title, description = :desc " +
               "WHERE task_id = :taskId")
    int updateTask(
            @Bind("taskId") int taskId,
            @Bind("projectId") int projectId,
            @Bind("stage") String stage,
            @Bind("title") String title,
            @Bind("desc") String description
    );
}