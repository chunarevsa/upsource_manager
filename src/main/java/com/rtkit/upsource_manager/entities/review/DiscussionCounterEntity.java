package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.payload.api.review.DiscussionCounter;

import javax.persistence.*;

@Entity
@Table(name = "discussion_counter")
public class DiscussionCounterEntity {

    @Id
    @Column(name = "discussion_counter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "count_id")
    public int count;
    public boolean hasUnresolved;
    public int unresolvedCount;
    public int resolvedCount;

    public DiscussionCounterEntity(DiscussionCounter discussionCounter) {
        this.count = discussionCounter.getCount();
        this.hasUnresolved = discussionCounter.getHasUnresolved();
        this.unresolvedCount = discussionCounter.getUnresolvedCount();
        this.resolvedCount = discussionCounter.getResolvedCount();
    }

    public DiscussionCounterEntity() {

    }

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

