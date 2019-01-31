package com.workflowstreamer.data.dao.mapper;

import com.workflowstreamer.core.ImmutableUser;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<ImmutableUser> {
    public ImmutableUser map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return ImmutableUser.builder()
                .userId(r.getInt("user_id"))
                .email(r.getString("email"))
                .username(r.getString("username"))
                .password(r.getString("password"))
                .build();
    }
}