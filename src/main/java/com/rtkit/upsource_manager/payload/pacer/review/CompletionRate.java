package com.rtkit.upsource_manager.payload.pacer.review;

public class CompletionRate {
    public int completedCount;
    public int reviewersCount;
    public boolean hasConcern;

    public int getCompletedCount() {
        return completedCount;
    }

    public int getReviewersCount() {
        return reviewersCount;
    }

    public boolean isHasConcern() {
        return hasConcern;
    }
}

