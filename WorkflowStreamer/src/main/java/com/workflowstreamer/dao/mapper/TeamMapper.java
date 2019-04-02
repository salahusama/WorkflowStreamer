package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableTeam;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapper implements ResultSetMapper<ImmutableTeam> {
    public ImmutableTeam map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableTeam.builder()
                .teamId(r.getInt("team_id"))
                .name(r.getString("name"))
                .description(r.getString("description"))
                .build();
    }
}