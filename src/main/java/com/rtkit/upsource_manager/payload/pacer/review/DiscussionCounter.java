package com.rtkit.upsource_manager.payload.pacer.review;

public class DiscussionCounter {
    public int count;
    public boolean hasUnresolved;
    public int unresolvedCount;
    public int resolvedCount;

    public int getCount() {
        return count;
    }

    public boolean isHasUnresolved() {
        return hasUnresolved;
    }

    public int getUnresolvedCount() {
        return unresolvedCount;
    }

    public int getResolvedCount() {
        return resolvedCount;
    }
}

