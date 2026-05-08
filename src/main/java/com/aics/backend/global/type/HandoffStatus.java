package com.aics.backend.global.type;

public enum HandoffStatus {
    REQUESTED("요청됨"),
    ASSIGNED("배정됨"),
    RESOLVED("해결됨"),
    CANCELLED("취소됨");

    private final String description;

    HandoffStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
