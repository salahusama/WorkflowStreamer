package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.core.ImmutableUserStage;
import com.workflowstreamer.dao.mapper.UserMapper;
import com.workflowstreamer.dao.mapper.UserStageMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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
    @SqlQuery("SELECT user_id, stage FROM user_stages WHERE user_id = :userId")
    Set<ImmutableUserStage> getUserStagesByUserId(@Bind("userId") int userId);

    @SqlUpdate("INSERT INTO user_stages (user_id, stage) VALUES (:userId, :stage)")
    void insertUserStage(@Bind("userId") int userId, @Bind("stage") String stage);
}