package com.rtkit.upsource_manager.payload.pacer.review;

import java.util.List;

public class Review {
    public ReviewId reviewId;
    public String title;
    public List<Participant> participants;
    public int state;
    public boolean isUnread;
    public boolean isReadyToClose;
    public boolean isRemoved;
    public Object createdAt;
    public String createdBy;
    public Object updatedAt;
    public CompletionRate completionRate;
    public DiscussionCounter discussionCounter;
    public boolean isMuted;
    public List<String> branch;

    public Review(ReviewId reviewId, String createdBy) {
        this.reviewId = reviewId;
        this.createdBy = createdBy;
    }

    public Review() {
    }

    public String getUpsourceLink(String reviewId) {
        return "https://codereview.ritperm.rt.ru/elk/review/" + reviewId;
    }

    public String getJiraLink(String title) {
        try {
            return title.substring(0, 38);
        } catch (Exception e) { return "";}
    }

    public String getClearTitle(String title) {
        if (title.length() < 39) {
            return getJiraLink(title);
        }
        return title.substring(39);
    }

    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUnread(boolean unread) {
        isUnread = unread;
    }

    public void setReadyToClose(boolean readyToClose) {
        isReadyToClose = readyToClose;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCompletionRate(CompletionRate completionRate) {
        this.completionRate = completionRate;
    }

    public void setDiscussionCounter(DiscussionCounter discussionCounter) {
        this.discussionCounter = discussionCounter;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public void setBranch(List<String> branch) {
        this.branch = branch;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }

    public String getTitle() {
        return title;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public int getState() {
        return state;
    }

    public boolean isUnread() {
        return isUnread;
    }

    public boolean isReadyToClose() {
        return isReadyToClose;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public CompletionRate getCompletionRate() {
        return completionRate;
    }

    public DiscussionCounter getDiscussionCounter() {
        return discussionCounter;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public List<String> getBranch() {
        return branch;
    }
}

