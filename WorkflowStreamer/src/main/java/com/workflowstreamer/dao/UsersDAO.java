package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.dao.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface UsersDAO {
    @Mapper(UserMapper.class)
    @SqlQuery("select user_id, username, password from users where username = :username")
    ImmutableUser getUserByUsername(@Bind("username") String username);

    @Mapper(UserMapper.class)
    @SqlQuery("select user_id, username, password from users where user_id = :user_id")
    ImmutableUser getUserById(@Bind("user_id") int userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO users (username, password) VALUES (:username, :password)")
    int insertUser(@Bind("username") String username, @Bind("password") String password);
}