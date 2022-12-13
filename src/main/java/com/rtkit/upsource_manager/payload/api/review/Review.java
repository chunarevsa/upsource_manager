package com.rtkit.upsource_manager.payload.api.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    @JsonProperty("reviewId")
    private ReviewId reviewId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("participants")
    private List<Participant> participants = null;
    @JsonProperty("state")
    private Integer state;
    @JsonProperty("isUnread")
    private Boolean isUnread;
    @JsonProperty("isReadyToClose")
    private Boolean isReadyToClose;
    @JsonProperty("branch")
    public List<String> branch;
    @JsonProperty("isRemoved")
    private Boolean isRemoved;
    @JsonProperty("createdAt")
    private Long createdAt;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("updatedAt")
    private Long updatedAt;
    @JsonProperty("completionRate")
    private CompletionRate completionRate;
    @JsonProperty("discussionCounter")
    private DiscussionCounter discussionCounter;
    @JsonProperty("deadline")
    public Long deadline;
    @JsonProperty("isMuted")
    private Boolean isMuted;
    @JsonProperty("mergeFromBranch")
    public String mergeFromBranch;
    @JsonProperty("mergeToBranch")
    public String mergeToBranch;

    /**
     * No args constructor for use in serialization
     */
    public Review() {
    }

    /**
     * @param reviewId
     * @param title
     * @param description
     * @param participants
     * @param state
     * @param isUnread
     * @param isReadyToClose
     * @param branch
     * @param isRemoved
     * @param createdAt
     * @param createdBy
     * @param updatedAt
     * @param completionRate
     * @param discussionCounter
     * @param deadline
     * @param isMuted
     * @param mergeFromBranch
     * @param mergeToBranch
     */
    public Review(ReviewId reviewId, String title, String description, List<Participant> participants,
                  Integer state, Boolean isUnread, Boolean isReadyToClose, List<String> branch,
                  Boolean isRemoved, Long createdAt, String createdBy, Long updatedAt,
                  CompletionRate completionRate, DiscussionCounter discussionCounter,
                  Long deadline, Boolean isMuted, String mergeFromBranch, String mergeToBranch) {
        this.reviewId = reviewId;
        this.title = title;
        this.description = description;
        this.participants = participants;
        this.state = state;
        this.isUnread = isUnread;
        this.isReadyToClose = isReadyToClose;
        this.branch = branch;
        this.isRemoved = isRemoved;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.completionRate = completionRate;
        this.discussionCounter = discussionCounter;
        this.deadline = deadline;
        this.isMuted = isMuted;
        this.mergeFromBranch = mergeFromBranch;
        this.mergeToBranch = mergeToBranch;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getUnread() {
        return isUnread;
    }

    public void setUnread(Boolean unread) {
        isUnread = unread;
    }

    public Boolean getReadyToClose() {
        return isReadyToClose;
    }

    public void setReadyToClose(Boolean readyToClose) {
        isReadyToClose = readyToClose;
    }

    public List<String> getBranch() {
        return branch;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public Boolean getRemoved() {
        return isRemoved;
    }

    public void setRemoved(Boolean removed) {
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

    public CompletionRate getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(CompletionRate completionRate) {
        this.completionRate = completionRate;
    }

    public DiscussionCounter getDiscussionCounter() {
        return discussionCounter;
    }

    public void setDiscussionCounter(DiscussionCounter discussionCounter) {
        this.discussionCounter = discussionCounter;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Boolean getMuted() {
        return isMuted;
    }

    public void setMuted(Boolean muted) {
        isMuted = muted;
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
        return "Review{" +
                "reviewId=" + reviewId +
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
