package com.workflowstreamer.core.analytics;

import java.util.Date;
import java.util.Optional;

public class MutableAnalyticsEvent implements BasicAnalyticsEvent {
    private String _id;
    private String eventName;
    private String eventType;
    private Date time;

    private Integer projectId;
    private Integer teamId;
    private Integer userId;
    private Integer taskId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Optional<Integer> getProjectId() {
        return Optional.ofNullable(projectId);
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Optional<Integer> getTeamId() {
        return Optional.ofNullable(teamId);
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Optional<Integer> getUserId() {
        return Optional.ofNullable(userId);
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Optional<Integer> getTaskId() {
        return Optional.ofNullable(taskId);
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
