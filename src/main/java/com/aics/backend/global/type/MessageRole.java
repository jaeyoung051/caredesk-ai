package com.aics.backend.global.type;

/**
 * 상담 메시지의 역할을 나타냅니다.
 */
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
