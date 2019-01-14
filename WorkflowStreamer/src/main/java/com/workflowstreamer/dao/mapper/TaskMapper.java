package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.core.enums.Priority;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements ResultSetMapper<ImmutableTask> {
    public ImmutableTask map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        ImmutableTask.Builder builder = ImmutableTask.builder()
                .taskId(r.getInt("task_id"))
                .projectId(r.getInt("project_id"))
                .creatorId(r.getInt("creator_id"))
                .stage(r.getString("stage"))
                .title(r.getString("title"))
                .description(r.getString("description"))
                .createdAt(r.getTimestamp("created_at"))
                .estimatedWork(r.getInt("est_work"));

        String priority = r.getString("priority");

        if (priority != null) {
            builder.priority(Priority.valueOf(priority));
        }

        return builder.build();
    }
}