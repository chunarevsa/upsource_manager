package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.payload.api.review.CompletionRate;

import javax.persistence.*;

@Entity
@Table(name = "completion_rate")
public class CompletionRateEntity {

    @Id
    @Column(name = "completion_rate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "completed_count")
    public int completedCount;
    public int reviewersCount;
    public boolean hasConcern;

    public CompletionRateEntity(CompletionRate completionRate) {
        this.completedCount = completionRate.getCompletedCount();
        this.reviewersCount = completionRate.getReviewersCount();
        this.hasConcern = completionRate.getHasConcern();
    }

    public CompletionRateEntity() {

    }

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

