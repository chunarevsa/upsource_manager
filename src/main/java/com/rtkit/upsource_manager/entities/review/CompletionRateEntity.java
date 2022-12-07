package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.payload.pacer.review.CompletionRate;
import org.hibernate.annotations.NaturalId;

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
        this.completedCount = completionRate.completedCount;
        this.reviewersCount = completionRate.reviewersCount;
        this.hasConcern = completionRate.hasConcern;
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

