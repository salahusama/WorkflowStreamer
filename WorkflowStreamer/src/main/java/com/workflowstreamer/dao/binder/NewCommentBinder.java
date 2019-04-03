package com.workflowstreamer.dao.binder;

import com.workflowstreamer.core.ImmutableNewComment;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(NewCommentBinder.NewCommentBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NewCommentBinder {
    class NewCommentBinderFactory implements BinderFactory {

        @Override
        public Binder build(Annotation t) {
            return (Binder<NewCommentBinder, ImmutableNewComment>) (query, bind, newComment) -> {
                query.bind("taskId", newComment.getTaskId());
                query.bind("creatorId", newComment.getCreatorId());
                query.bind("text", newComment.getText());
            };
        }
    }
}
