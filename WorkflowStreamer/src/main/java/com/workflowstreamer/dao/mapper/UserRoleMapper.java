package com.workflowstreamer.dao.mapper;

import com.workflowstreamer.core.ImmutableUserRole;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRoleMapper implements ResultSetMapper<ImmutableUserRole> {
    public ImmutableUserRole map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableUserRole.builder()
                .userId(r.getInt("user_id"))
                .email(r.getString("email"))
                .username(r.getString("username"))
                .role(r.getString("title"))
                .roleDescription(r.getString("description"))
                .build();
    }
}