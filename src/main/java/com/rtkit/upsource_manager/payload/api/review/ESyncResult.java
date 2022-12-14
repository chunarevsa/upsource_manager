package com.rtkit.upsource_manager.payload.api.review;

public enum ESyncResult {
    NONE(1),
    FOUND(2),
    IMPORTED(3);

    private final Integer number;

    ESyncResult(Integer number) {
        this.number = number;
    }
}
