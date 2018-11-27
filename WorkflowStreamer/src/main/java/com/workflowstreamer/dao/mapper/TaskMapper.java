package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableTask;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements ResultSetMapper<ImmutableTask> {
    public ImmutableTask map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableTask.builder()
                .taskId(r.getInt("task_id"))
                .creatorId(r.getInt("creator_id"))
                .title(r.getString("title"))
                .description(r.getString("description"))
                .createdAt(r.getTimestamp("created_at"))
                .build();
    }
}