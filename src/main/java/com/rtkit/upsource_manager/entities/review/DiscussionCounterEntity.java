package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.payload.pacer.review.DiscussionCounter;

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
        this.count = discussionCounter.count;
        this.hasUnresolved = discussionCounter.hasUnresolved;
        this.unresolvedCount = discussionCounter.unresolvedCount;
        this.resolvedCount = discussionCounter.resolvedCount;
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

