package com.workflowstreamer.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UsersDAO {
    @SqlQuery("select username from users where user_id = :id")
    public String getUsernameById(@Bind("id") int id);
}