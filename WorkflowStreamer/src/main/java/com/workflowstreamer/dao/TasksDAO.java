package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableNewTask;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.core.enums.Priority;
import com.workflowstreamer.dao.binder.NewTaskBinder;
import com.workflowstreamer.dao.mapper.TaskMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.Date;
import java.util.List;
import java.util.Set;

@UseStringTemplate3StatementLocator
public interface TasksDAO {
    @Mapper(TaskMapper.class)
    @SqlQuery("SELECT * FROM tasks " +
              "JOIN projects USING (project_id) " +
              "WHERE team_id IN (<ids>)")
    Set<ImmutableTask> getTasksByTeamIds(@BindIn("ids") List<Integer> teamIds);

    @Mapper(TaskMapper.class)
    @SqlQuery("SELECT * FROM tasks WHERE creator_id = :userId")
    Set<ImmutableTask> getTasksByUser(@Bind("userId") int userId);

    @Mapper(TaskMapper.class)
    @SqlQuery("SELECT * FROM tasks WHERE project_id = :projectId")
    Set<ImmutableTask> getTasksByProjectId(@Bind("projectId") int projectId);

    @Mapper(TaskMapper.class)
    @SqlQuery("SELECT * FROM tasks WHERE task_id = :id")
    ImmutableTask getTaskById(@Bind("id") int id);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO tasks (project_id, creator_id, stage, title, description, created_at, priority, est_work)" +
               "VALUES (:projectId, :creatorId, :stage, :title, :description, :createdAt, :priority, :estimatedWork)")
    int insertTask(@NewTaskBinder ImmutableNewTask newTask);

    @SqlUpdate("UPDATE tasks " +
               "SET project_id = :projectId, stage = :stage, title = :title, description = :desc, priority = :priority, est_work = :est_work, due_date = :due_date " +
               "WHERE task_id = :taskId")
    int updateTask(
            @Bind("taskId") int taskId,
            @Bind("projectId") int projectId,
            @Bind("stage") String stage,
            @Bind("title") String title,
            @Bind("desc") String description,
            @Bind("priority") Priority priority,
            @Bind("est_work") Integer estimatedWork,
            @Bind("due_date") Date dueDate
    );
}
