package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableStage;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StageMapper implements ResultSetMapper<ImmutableStage> {
    public ImmutableStage map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableStage.builder()
                .stage(r.getString("stage"))
                .viewOrder(r.getInt("view_order"))
                .build();
    }
}
