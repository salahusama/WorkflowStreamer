package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableComment;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements ResultSetMapper<ImmutableComment> {
    public ImmutableComment map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableComment.builder()
                .commentId(r.getInt("comment_id"))
                .creatorId(r.getInt("creator_id"))
                .taskId(r.getInt("task_id"))
                .text(r.getString("text"))
                 .build();
    }
}