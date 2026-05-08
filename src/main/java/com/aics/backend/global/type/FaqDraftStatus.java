package com.aics.backend.global.type;

public enum FaqDraftStatus {
    PENDING("대기중"),
    APPROVED("승인됨"),
    REJECTED("거부됨");

    private final String description;

    FaqDraftStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
