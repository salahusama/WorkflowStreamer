package com.workflowstreamer.core.analytics;

import java.util.Date;

public class MutableAnalyticsEvent {
    private String _id;
    private String eventName;
    private String eventType;
    private Date time;

    private int projectId;
    private int teamId;
    private int userId;
    private int taskId;

    public MutableAnalyticsEvent() {}

    public MutableAnalyticsEvent(ImmutableAnalyticsEvent analyticsEvent) {
        set_id(analyticsEvent.getId().orElse(null));
        setEventName(analyticsEvent.getEventName());
        setEventType(analyticsEvent.getEventType());
        setTime(analyticsEvent.getTime());

        if (analyticsEvent.getProjectId().isPresent())  setProjectId(analyticsEvent.getProjectId().get());
        if (analyticsEvent.getTeamId().isPresent())     setTeamId(analyticsEvent.getTeamId().get());
        if (analyticsEvent.getUserId().isPresent())     setUserId(analyticsEvent.getUserId().get());
        if (analyticsEvent.getTaskId().isPresent())     setTaskId(analyticsEvent.getTaskId().get());
    }

    public String get_id() {
        return _id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public Date getTime() {
        return time;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
