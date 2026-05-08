package com.aics.backend.global.type;

public enum MessageRole {
    USER("사용자"),
    ASSISTANT("어시스턴트"),
    AGENT("상담원"),
    SYSTEM("시스템");

    private final String description;

    MessageRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
