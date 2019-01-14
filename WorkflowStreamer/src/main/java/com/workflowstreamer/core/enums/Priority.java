package com.workflowstreamer.core.enums;

public enum Priority {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    private final int code;

    Priority(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
