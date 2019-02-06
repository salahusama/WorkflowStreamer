package com.workflowstreamer.dao.binder;

import com.workflowstreamer.core.ImmutableNewTask;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;
import java.util.Date;

@BindingAnnotation(NewTaskBinder.NewTaskBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NewTaskBinder {
    class NewTaskBinderFactory implements BinderFactory {

        @Override
        public Binder build(Annotation t) {
            return (Binder<NewTaskBinder, ImmutableNewTask>) (query, bind, newTask) -> {
                query.bind("title", newTask.getTitle());
                query.bind("stage", newTask.getStage());
                query.bind("creatorId", newTask.getCreatorId());
                query.bind("projectId", newTask.getProjectId());
                query.bind("createdAt", new Date());
                query.bind("dueDate", newTask.getDueDate().orElse(null));
                query.bind("priority", newTask.getPriority().orElse(null));
                query.bind("description", newTask.getDescription().orElse(null));
                query.bind("estimatedWork", newTask.getEstimatedWork().orElse(null));
            };
        }
    }
}
