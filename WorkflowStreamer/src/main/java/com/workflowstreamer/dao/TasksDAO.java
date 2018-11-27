package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.mapper.TaskMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface TasksDAO {
    @SqlQuery("select task_id, title, description, creator_id, created_at from tasks")
    @Mapper(TaskMapper.class)
    Set<ImmutableTask> getAllTasks();

    @SqlQuery("select task_id, title, description, creator_id, created_at from tasks where task_id = :id")
    @Mapper(TaskMapper.class)
    ImmutableTask getTaskById(@Bind("id") int id);
}