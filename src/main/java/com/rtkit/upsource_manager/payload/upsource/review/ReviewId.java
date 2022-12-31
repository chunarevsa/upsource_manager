package com.rtkit.upsource_manager.payload.upsource.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewId {

    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("reviewId")
    private String reviewId;

    /**
     * No args constructor for use in serialization
     */
    public ReviewId() {
    }

    /**
     * @param projectId
     * @param reviewId
     */
    public ReviewId(String projectId, String reviewId) {
        super();
        this.projectId = projectId;
        this.reviewId = reviewId;
    }

    @JsonProperty("projectId")
    public String getProjectId() {
        return projectId;
    }

    @JsonProperty("projectId")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("reviewId")
    public String getReviewId() {
        return reviewId;
    }

    @JsonProperty("reviewId")
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "ReviewId{" +
                "projectId='" + projectId + '\'' +
                ", reviewId='" + reviewId + '\'' +
                '}';
    }
}
