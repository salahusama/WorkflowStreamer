package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableProject;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements ResultSetMapper<ImmutableProject> {
    public ImmutableProject map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableProject.builder()
                .projectId(r.getInt("project_id"))
                .creatorId(r.getInt("creator_id"))
                .name(r.getString("name"))
                .description(r.getString("description"))
                .build();
    }
}