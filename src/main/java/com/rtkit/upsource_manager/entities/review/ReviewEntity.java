package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.entities.participant.ParticipantEntity;
import com.rtkit.upsource_manager.payload.api.review.Review;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "review")
public class ReviewEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", allocationSize = 1)
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id", unique = true)
    public ReviewIdEntity reviewId;

    @Column(name = "title", length = 500)
    public String title;

    @Column(name = "description", length = 500)
    public String description;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "review_participants",
            joinColumns = {@JoinColumn(name = "review_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "participant_id", referencedColumnName = "participant_id")})
    public Set<ParticipantEntity> participants;
    public int state;
    public boolean isUnread;
    public boolean isReadyToClose;
    @ElementCollection
    public List<String> branch;
    public boolean isRemoved;
    public Long createdAt;
    public String createdBy;
    public Long updatedAt;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "completion_rate_id")
    public CompletionRateEntity completionRate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "discussion_counter_id")
    public DiscussionCounterEntity discussionCounter;

    public Long deadline;
    public boolean isMuted;
    @Column(name = "mergeFromBranch")
    public String mergeFromBranch;
    @Column(name = "mergeToBranch")
    public String mergeToBranch;


    public ReviewEntity() {
    }

    /**
     * Получение сущности из DTO (без Participants)
     */
    public ReviewEntity(Review review) {
        this.reviewId = new ReviewIdEntity(review.getReviewId());
        this.title = review.getTitle();
        this.description = review.getDescription();
        if (!review.getParticipants().isEmpty()) {
            this.participants = review.getParticipants().stream().map(ParticipantEntity::new).collect(Collectors.toSet());
        }
        this.state = review.getState();
        this.isUnread = review.getUnread();
        this.isReadyToClose = review.getReadyToClose();
        this.branch = review.getBranch();
        this.isRemoved = review.getRemoved();
        this.createdAt = review.getCreatedAt();
        this.createdBy = review.getCreatedBy();
        this.updatedAt = review.getUpdatedAt();
        this.completionRate = new CompletionRateEntity(review.getCompletionRate());
        this.discussionCounter = new DiscussionCounterEntity(review.getDiscussionCounter());
        this.deadline = review.getDeadline();
        this.isMuted = review.getMuted();
        this.mergeFromBranch = review.getMergeFromBranch();
        this.mergeToBranch = review.getMergeToBranch();

    }

    public String getUpsourceLink(String ReviewIdEntity) {
        return "https://codereview.ritperm.rt.ru/elk/review/" + ReviewIdEntity;
    }

    public String getJiraLink(String title) {
        try {
            return title.substring(0, 38);
        } catch (Exception e) {
            return "";
        }
    }

    public String getClearTitle(String title) {
        if (title.length() < 39) {
            return getJiraLink(title);
        }
        return title.substring(39);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return reviewId.getReviewId().equals(that.reviewId.getReviewId()) && updatedAt.equals(that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId.getReviewId(), updatedAt);
    }


    public Long getId() {
        return id;
    }

    public ReviewIdEntity getReviewId() {
        return reviewId;
    }

    public void setReviewId(ReviewIdEntity reviewId) {
        this.reviewId = reviewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ParticipantEntity> participants) {
        this.participants = participants;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public boolean isReadyToClose() {
        return isReadyToClose;
    }

    public void setReadyToClose(boolean readyToClose) {
        isReadyToClose = readyToClose;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CompletionRateEntity getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(CompletionRateEntity completionRate) {
        this.completionRate = completionRate;
    }

    public DiscussionCounterEntity getDiscussionCounter() {
        return discussionCounter;
    }

    public void setDiscussionCounter(DiscussionCounterEntity discussionCounter) {
        this.discussionCounter = discussionCounter;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public String getMergeFromBranch() {
        return mergeFromBranch;
    }

    public void setMergeFromBranch(String mergeFromBranch) {
        this.mergeFromBranch = mergeFromBranch;
    }

    public String getMergeToBranch() {
        return mergeToBranch;
    }

    public void setMergeToBranch(String mergeToBranch) {
        this.mergeToBranch = mergeToBranch;
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", reviewId=" + reviewId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", participants=" + participants +
                ", state=" + state +
                ", isUnread=" + isUnread +
                ", isReadyToClose=" + isReadyToClose +
                ", branch=" + branch +
                ", isRemoved=" + isRemoved +
                ", createdAt=" + createdAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedAt=" + updatedAt +
                ", completionRate=" + completionRate +
                ", discussionCounter=" + discussionCounter +
                ", deadline=" + deadline +
                ", isMuted=" + isMuted +
                ", mergeFromBranch='" + mergeFromBranch + '\'' +
                ", mergeToBranch='" + mergeToBranch + '\'' +
                '}';
    }
}

