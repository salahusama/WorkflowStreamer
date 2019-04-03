package com.workflowstreamer.dao;

import com.workflowstreamer.core.ImmutableComment;
import com.workflowstreamer.core.ImmutableNewComment;
import com.workflowstreamer.dao.binder.NewCommentBinder;
import com.workflowstreamer.dao.mapper.CommentMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Set;

public interface CommentsDAO {
    @Mapper(CommentMapper.class)
    @SqlQuery("SELECT * FROM task_comments WHERE comment_id = :commentId")
    ImmutableComment getCommentById(@Bind("commentId") int commentId);

    @Mapper(CommentMapper.class)
    @SqlQuery("SELECT * FROM task_comments WHERE task_id = :taskId")
    Set<ImmutableComment> getCommentsForTask(@Bind("taskId") int taskId);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO task_comments (task_id, creator_id, text) VALUES (:taskId, :creatorId, :text)")
    int addComment(@NewCommentBinder ImmutableNewComment newComment);
}