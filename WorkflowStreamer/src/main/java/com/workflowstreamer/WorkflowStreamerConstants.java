package com.workflowstreamer;

public interface WorkflowStreamerConstants {
    String EVENTS_COLLECTION_NAME = "events";

    interface Events {
        String TASK_INTERACTION = "task-interaction";
    }

    interface Types {
        String CREATED_TASK = "created-task";
    }
}
