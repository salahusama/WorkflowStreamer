package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.core.ImmutableUserRole;
import com.workflowstreamer.dao.binder.NewTeamBinder;
import com.workflowstreamer.dao.mapper.TeamMapper;
import com.workflowstreamer.dao.mapper.UserRoleMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface TeamsDAO {
    @Mapper(TeamMapper.class)
    @SqlQuery("SELECT * FROM teams WHERE team_id = :id")
    ImmutableTeam getTeamById(@Bind("id") int id);

    @Mapper(TeamMapper.class)
    @SqlQuery("SELECT * FROM teams " +
              "JOIN user_teams USING (team_id) " +
              "WHERE user_id = :userId")
    Set<ImmutableTeam> getTeamsByUser(@Bind("userId") int userId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO teams (name, description) VALUES (:name, :description)")
    int insertTeam(@NewTeamBinder ImmutableNewTeam newTeam);

    @SqlQuery("SELECT team_id FROM tasks " +
               "JOIN projects USING (project_id) " +
               "WHERE task_id = :taskId")
    int getTeamIdByTaskId(@Bind("taskId") int taskId);

    @Mapper(UserRoleMapper.class)
    @SqlQuery("SELECT user_id, username, email, title, description FROM user_teams " +
              "JOIN roles USING (role_id) " +
              "JOIN users USING (user_id) " +
              "WHERE team_id = :teamId")
    Set<ImmutableUserRole> getTeamMembers(@Bind("teamId") int teamId);

    @Mapper(UserRoleMapper.class)
    @SqlQuery("SELECT user_id, username, email, title, description FROM user_teams " +
            "JOIN roles USING (role_id) " +
            "JOIN users USING (user_id) " +
            "WHERE team_id = :teamId AND user_id = :userId")
    ImmutableUserRole getTeamMemberByTeamAndUserId(@Bind("teamId") int teamId, @Bind("userId") int userId);

    @SqlUpdate("INSERT INTO user_teams (team_id, user_id, role_id) VALUES (:teamId, :userId, :roleId)")
    void addUserToTeam(@Bind("teamId") int teamId, @Bind("userId") int userId, @Bind("roleId") int roleId);
}