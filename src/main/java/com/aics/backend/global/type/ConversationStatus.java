package com.aics.backend.global.type;

/**
 * 상담방의 상태를 나타냅니다.
 */
public enum ConversationStatus {
    OPEN("진행중"),
    WAITING_AGENT("상담원대기중"),
    CLOSED("종료");

    private final String description;

    ConversationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
