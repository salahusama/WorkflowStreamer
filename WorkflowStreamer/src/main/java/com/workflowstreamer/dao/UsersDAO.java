package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
import com.workflowstreamer.dao.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface UsersDAO {
    @Mapper(UserMapper.class)
    @SqlQuery("SELECT user_id, email, username, password FROM users WHERE username = :username")
    ImmutableUser getUserByUsername(@Bind("username") String username);

    @Mapper(UserMapper.class)
    @SqlQuery("SELECT * FROM users WHERE email = :email")
    ImmutableUser getUserByEmail(@Bind("email") String email);

    @Mapper(UserMapper.class)
    @SqlQuery("SELECT user_id, email, username, password FROM users WHERE user_id = :user_id")
    ImmutableUser getUserById(@Bind("user_id") int userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO users (email, username, password) VALUES (:email, :username, :password)")
    int insertUser(@Bind("email") String email, @Bind("username") String username, @Bind("password") String password);

    @SqlUpdate("INSERT INTO user_stages (user_id, stage, view_order) VALUES (:userId, :stage, :viewOrder)")
    void insertUserStage(@BindBean ImmutableUserStage userStage);
}
