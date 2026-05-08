package com.aics.backend.global.type;

/**
 * 지식 문서의 인덱싱 상태를 나타냅니다.
 */
public enum IndexStatus {
    PENDING("대기중"),
    INDEXING("인덱싱중"),
    INDEXED("인덱싱완료"),
    FAILED("실패");

    private final String description;

    IndexStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
