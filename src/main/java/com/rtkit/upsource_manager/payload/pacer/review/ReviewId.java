package com.rtkit.upsource_manager.payload.pacer.review;

public class ReviewId {
	public String projectId;
	public String reviewId;

	public String getProjectId() {
		return projectId;
	}

	public String getReviewId() {
		return reviewId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	public ReviewId(String projectId, String reviewId) {
		this.projectId = projectId;
		this.reviewId = reviewId;
	}

	public ReviewId() {}

	@Override
	public String toString() {
		return "reviewId='" + reviewId + '\'';
	}
}
