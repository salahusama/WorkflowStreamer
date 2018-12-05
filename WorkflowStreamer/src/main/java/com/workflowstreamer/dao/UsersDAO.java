package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableLoginData;
import com.workflowstreamer.core.ImmutableUser;
import com.workflowstreamer.dao.mapper.TaskMapper;
import com.workflowstreamer.dao.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface UsersDAO {
    @Mapper(UserMapper.class)
    @SqlQuery("select user_id, username, password from users where username = :username")
    ImmutableUser getUserByUsername(@Bind("username") String username);
}