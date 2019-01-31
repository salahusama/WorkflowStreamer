package com.workflowstreamer.data.dao.mapper;

import com.workflowstreamer.core.ImmutableUserStage;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStageMapper implements ResultSetMapper<ImmutableUserStage> {
    public ImmutableUserStage map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableUserStage.builder()
                .userId(r.getInt("user_id"))
                .stage(r.getString("stage"))
                .viewOrder(r.getInt("view_order"))
                .build();
    }
}
