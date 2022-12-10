package com.rtkit.upsource_manager.payload.api.response.reviewList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "reviewId",
        "title",
        "participants",
        "state",
        "isUnread",
        "isReadyToClose",
        "isRemoved",
        "createdAt",
        "createdBy",
        "updatedAt",
        "completionRate",
        "discussionCounter",
        "isMuted"
})
@Generated("jsonschema2pojo")
public class Review {

    public List<String> branch;
    public String description;
    @JsonProperty("reviewId")
    private ReviewId reviewId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("participants")
    private List<Participant> participants = null;
    @JsonProperty("state")
    private Integer state;
    @JsonProperty("isUnread")
    private Boolean isUnread;
    @JsonProperty("isReadyToClose")
    private Boolean isReadyToClose;
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
    @JsonProperty("isMuted")
    private Boolean isMuted;

    /**
     * No args constructor for use in serialization
     */
    public Review() {
    }

    /**
     * @param completionRate
     * @param title
     * @param isUnread
     * @param isReadyToClose
     * @param createdAt
     * @param isRemoved
     * @param createdBy
     * @param discussionCounter
     * @param state
     * @param reviewId
     * @param isMuted
     * @param participants
     * @param updatedAt
     */
    public Review(ReviewId reviewId, String title, List<Participant> participants, Integer state, Boolean isUnread, Boolean isReadyToClose, Boolean isRemoved, Long createdAt, String createdBy, Long updatedAt, CompletionRate completionRate, DiscussionCounter discussionCounter, Boolean isMuted) {
        super();
        this.reviewId = reviewId;
        this.title = title;
        this.participants = participants;
        this.state = state;
        this.isUnread = isUnread;
        this.isReadyToClose = isReadyToClose;
        this.isRemoved = isRemoved;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.completionRate = completionRate;
        this.discussionCounter = discussionCounter;
        this.isMuted = isMuted;
    }

    @JsonProperty("reviewId")
    public ReviewId getReviewId() {
        return reviewId;
    }

    @JsonProperty("reviewId")
    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("participants")
    public List<Participant> getParticipants() {
        return participants;
    }

    @JsonProperty("participants")
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @JsonProperty("state")
    public Integer getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(Integer state) {
        this.state = state;
    }

    @JsonProperty("isUnread")
    public Boolean getIsUnread() {
        return isUnread;
    }

    @JsonProperty("isUnread")
    public void setIsUnread(Boolean isUnread) {
        this.isUnread = isUnread;
    }

    @JsonProperty("isReadyToClose")
    public Boolean getIsReadyToClose() {
        return isReadyToClose;
    }

    @JsonProperty("isReadyToClose")
    public void setIsReadyToClose(Boolean isReadyToClose) {
        this.isReadyToClose = isReadyToClose;
    }

    @JsonProperty("isRemoved")
    public Boolean getIsRemoved() {
        return isRemoved;
    }

    @JsonProperty("isRemoved")
    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    @JsonProperty("createdAt")
    public Long getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("updatedAt")
    public Long getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("completionRate")
    public CompletionRate getCompletionRate() {
        return completionRate;
    }

    @JsonProperty("completionRate")
    public void setCompletionRate(CompletionRate completionRate) {
        this.completionRate = completionRate;
    }

    @JsonProperty("discussionCounter")
    public DiscussionCounter getDiscussionCounter() {
        return discussionCounter;
    }

    @JsonProperty("discussionCounter")
    public void setDiscussionCounter(DiscussionCounter discussionCounter) {
        this.discussionCounter = discussionCounter;
    }

    @JsonProperty("isMuted")
    public Boolean getIsMuted() {
        return isMuted;
    }

    @JsonProperty("isMuted")
    public void setIsMuted(Boolean isMuted) {
        this.isMuted = isMuted;
    }

}
