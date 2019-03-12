package com.workflowstreamer.dao.binder;

import com.workflowstreamer.core.ImmutableNewProject;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(NewProjectBinder.NewProjectBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NewProjectBinder {
    class NewProjectBinderFactory implements BinderFactory {

        @Override
        public Binder build(Annotation t) {
            return (Binder<NewProjectBinder, ImmutableNewProject>) (query, bind, newProject) -> {
                query.bind("creatorId", newProject.getCreatorId());
                query.bind("teamId", newProject.getTeamId());
                query.bind("name", newProject.getName());
                query.bind("description", newProject.getDescription().orElse(null));
            };
        }
    }
}
