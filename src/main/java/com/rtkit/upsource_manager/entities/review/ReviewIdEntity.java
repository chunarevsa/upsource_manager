package com.rtkit.upsource_manager.entities.review;

import com.rtkit.upsource_manager.payload.pacer.review.ReviewId;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class ReviewIdEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** elk-apelk */
	@JoinColumn(name ="project_id", unique = true)
	public String projectId;
	/**  APELK-CR-462 */
	@NaturalId
	@JoinColumn(name ="review_id", unique = true)
	public String reviewId;

	public ReviewIdEntity(ReviewId reviewId) {
		this.projectId = reviewId.projectId;
		this.reviewId = reviewId.reviewId;
	}

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

	public ReviewIdEntity(String projectId, String reviewId) {
		this.projectId = projectId;
		this.reviewId = reviewId;
	}

	public ReviewIdEntity() {}

	@Override
	public String toString() {
		return "reviewId='" + reviewId + '\'';
	}
}