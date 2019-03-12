package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableNewTeam;
import com.workflowstreamer.core.ImmutableTeam;
import com.workflowstreamer.dao.binder.NewTeamBinder;
import com.workflowstreamer.dao.mapper.TeamMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
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

    @SqlUpdate("INSERT INTO user_teams (user_id, team_id) VALUES (:userId, :teamId)")
    void addUserToTeam(@Bind("userId") int userId, @Bind("teamId") int teamId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO teams (name, description) VALUES (:name, :description)")
    int insertTeam(@NewTeamBinder ImmutableNewTeam newTeam);
}