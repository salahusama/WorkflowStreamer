package com.workflowstreamer.resources;

import com.google.common.collect.ImmutableSet;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.dao.TasksDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TasksResourceTest {
    private static final int TASK_ID = 123;
    private static final int PROJECT_ID = 12;
    private static final ImmutableTask TASK = ImmutableTask.builder()
            .taskId(TASK_ID)
            .projectId(PROJECT_ID)
            .creatorId(5)
            .stage("A stage")
            .title("A title")
            .description("This is a fake task")
            .createdAt(Timestamp.from(Instant.now()))
            .build();
    private static final ImmutableSet<ImmutableTask> TASKS = ImmutableSet.of(
            TASK,
            ImmutableTask.builder()
                    .taskId(125)
                    .projectId(PROJECT_ID)
                    .creatorId(5)
                    .stage("Another stage")
                    .title("Another title")
                    .description("This is another fake task")
                    .createdAt(Timestamp.from(Instant.now()))
                    .build()
    );

    @Mock
    private TasksDAO tasksDAO;
    private TasksResource tasksResource;

    @Before
    public void init() {
        when(tasksDAO.getAllTasks()).thenReturn(TASKS);
        when(tasksDAO.getTaskById(TASK_ID)).thenReturn(TASK);

        tasksResource = new TasksResource(tasksDAO);
    }

    @Test
    public void itReturnsSetOfTasksCorrectly() {
        assertThat(tasksResource.getTasks()).isEqualTo(TASKS);
    }

    @Test
    public void itSaysHelloToStranger() {
        assertThat(tasksResource.getTaskById(TASK_ID)).isEqualTo(TASK);
    }
}