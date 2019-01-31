package com.workflowstreamer.data.dao.mapper;

import com.workflowstreamer.core.ImmutableProject;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectMapper implements ResultSetMapper<ImmutableProject> {
    public ImmutableProject map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableProject.builder()
                .projectId(r.getInt("project_id"))
                .creatorId(r.getInt("creator_id"))
                .name(r.getString("name"))
                .description(Optional.ofNullable(r.getString("description")))
                .build();
    }
}