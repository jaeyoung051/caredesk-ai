package com.aics.backend.global.type;

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
