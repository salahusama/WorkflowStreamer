package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.mapper.TaskMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.sql.Timestamp;
import java.util.Set;

public interface TasksDAO {
    @Mapper(TaskMapper.class)
    @SqlQuery("select task_id, title, description, creator_id, created_at from tasks")
    Set<ImmutableTask> getAllTasks();

    @Mapper(TaskMapper.class)
    @SqlQuery("select task_id, title, description, creator_id, created_at from tasks where task_id = :id")
    ImmutableTask getTaskById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO TASKS (title, description, creator_id, created_at) VALUES (:title, :desc, :user, :createdAt)")
    void insertTask(@Bind("title") String title, @Bind("desc") String description, @Bind("user")int userId, @Bind("createdAt") Timestamp createdAt);
}