package com.workflowstreamer.dao.binder;

import com.workflowstreamer.core.ImmutableNewTeam;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(NewTeamBinder.NewProjectBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NewTeamBinder {
    class NewProjectBinderFactory implements BinderFactory {

        @Override
        public Binder build(Annotation t) {
            return (Binder<NewTeamBinder, ImmutableNewTeam>) (query, bind, newTeam) -> {
                query.bind("name", newTeam.getName());
                query.bind("description", newTeam.getDescription());
            };
        }
    }
}
