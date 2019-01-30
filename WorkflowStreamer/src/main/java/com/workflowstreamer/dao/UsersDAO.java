package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
import com.workflowstreamer.dao.mapper.UserMapper;
import com.workflowstreamer.dao.mapper.UserStageMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface UsersDAO {
    @Mapper(UserMapper.class)
    @SqlQuery("select user_id, email, username, password from users where username = :username")
    ImmutableUser getUserByUsername(@Bind("username") String username);

    @Mapper(UserMapper.class)
    @SqlQuery("select user_id, email, username, password from users where user_id = :user_id")
    ImmutableUser getUserById(@Bind("user_id") int userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO users (email, username, password) VALUES (:email, :username, :password)")
    int insertUser(@Bind("email") String email, @Bind("username") String username, @Bind("password") String password);

    @Mapper(UserStageMapper.class)
    @SqlQuery("SELECT * FROM user_stages WHERE user_id = :userId")
    Set<ImmutableUserStage> getUserStagesByUserId(@Bind("userId") int userId);

    @SqlUpdate("INSERT INTO user_stages (user_id, stage, view_order) VALUES (:userId, :stage, :viewOrder)")
    void insertUserStage(@BindBean ImmutableUserStage userStage);
}
