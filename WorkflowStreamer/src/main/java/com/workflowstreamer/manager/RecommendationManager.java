package com.workflowstreamer.manager;

import com.google.common.collect.ImmutableSet;
import com.workflowstreamer.core.ImmutableTask;
import com.workflowstreamer.core.enums.Priority;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationManager {
    private static final int WEIGHT1 = 1;
    private static final int WEIGHT2 = 2;
    private static final int WEIGHT3 = 3;
    private static final int WEIGHT4 = 4;
    private static final int WEIGHT5 = 5;

    private static final ImmutableSet IMP_STAGES = ImmutableSet.of("In Review", "In Progress");
    private static final ImmutableSet MED_STAGES = ImmutableSet.of("back Log");
    private static final ImmutableSet LOW_STAGES = ImmutableSet.of("Ice Box", "Closed", "Done");

    public static Set<ImmutableTask> addRecommendationToTasks(Set<ImmutableTask> tasks) {
        if (tasks.size() == 0) {
            return tasks;
        }

        // Get sorted scores
        List<Integer> scores = tasks.stream()
                .map(RecommendationManager::getTaskScore)
                .sorted()
                .collect(Collectors.toList());

        // 80th percentile
        double perc80 = scores.size() * 0.80;
        int minScoreToRecommend = scores.get((int) perc80);

        return tasks.stream()
                .map(task -> {
                    if (getTaskScore(task) > minScoreToRecommend) {
                        return task.withIsRecommended(true);
                    } else {
                        return task.withIsRecommended(false);
                    }
                })
                .collect(Collectors.toSet());
    }

    public static int getTaskScore(ImmutableTask task) {
        int score = 0; // Max should be 100

        Priority priority = task.getPriority().orElse(Priority.LOW);
        String stage = task.getStage();
        Timestamp createdAt = task.getCreatedAt();
        Optional<Integer> estimatedWork = task.getEstimatedWork();
        Optional<Date> dueDate = task.getDueDate();

        // Priority
        int priorityScore = WEIGHT5 * priority.getCode() * 50;

        // Stage
        // HIGH -> In Review, In Progress
        // MED  -> Back Log
        // LOW  -> Ice Box, Closed, Done - 0
        int stageScore = 0;
        if (MED_STAGES.contains(stage)) {
            stageScore = 50;
        } else if (IMP_STAGES.contains(stage)) {
            stageScore = 100;
        }
        stageScore = WEIGHT4 * stageScore;

        // CreatedAt
        // Older tasks get priority
        // 1 Month Old -> LOW
        // 2 Month Old -> MEDIUM
        // 3+ Month Old -> HIGH
        long msSinceCreated = new Date().getTime() - createdAt.getTime();
        long monthsSinceCreated = msSinceCreated / (2592000000L);
        int createdAtScore = 0;
        if (monthsSinceCreated >= 3) {
            createdAtScore = 100;
        } else if (monthsSinceCreated == 2) {
            createdAtScore = 50;
        } else if (monthsSinceCreated == 1) {
            createdAtScore = 25;
        }
        createdAtScore = WEIGHT2 * createdAtScore;

        // Due Date
        // <= 1 week -> HIGH
        // <= 2 week -> Medium
        // Low
        int dueDateScore = 0;
        if (dueDate.isPresent()) {
            long msTillDue = new Date().getTime() - dueDate.get().getTime();
            long weeksTillDue = msTillDue / (604800000L);
            if (weeksTillDue <= 1) {
                dueDateScore = 100;
            } else if (weeksTillDue <= 2) {
                dueDateScore = 50;
            }
        }
        dueDateScore = WEIGHT3 * dueDateScore;

        // Estimated work should play a factor with due date
        // High work, low weeksTill Due -> high score
        int estimatedWorkScore = 0;
        if (estimatedWork.isPresent()) {
            if (estimatedWork.get() > 70 && dueDateScore == 100) {
                estimatedWorkScore = 100;
            } else if (estimatedWork.get() > 50 && dueDateScore >= 50) {
                estimatedWorkScore = 50;
            }
        }
        estimatedWorkScore = WEIGHT4 * estimatedWorkScore;

        /* Each score is out of 100 */

        return (priorityScore + stageScore + createdAtScore + dueDateScore + estimatedWorkScore) / 5;
    }
}
