package com.likelion.collab_session.domain.record.entity;

public enum GrowthTag {
    NEW_LEAF,      // 새잎
    LEAF_GROWTH,   // 잎 성장
    BLOOM,         // 꽃 개화
    LEAF_CHANGE,   // 잎 변화
    FAREWELL;       // 작별 (저장 시 plants.died_at 갱신 트리거)
    /** 프론트 호환용 소문자 태그 */
    public String toFrontTag() {
        return switch (this) {
            case NEW_LEAF -> "sprout";
            case LEAF_GROWTH, LEAF_CHANGE -> "leaf";
            case BLOOM -> "bloom";
            case FAREWELL -> "farewell";
        };
    }
}