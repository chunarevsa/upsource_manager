package com.rtkit.upsource_manager.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.rtkit.upsource_manager.payload.pacer.changes.ChangesRoot
import com.rtkit.upsource_manager.payload.pacer.review.Participant
import com.rtkit.upsource_manager.payload.pacer.review.ParticipantRoot
import com.rtkit.upsource_manager.payload.pacer.review.Review
import com.rtkit.upsource_manager.payload.pacer.review.ReviewRoot
import com.rtkit.upsource_manager.entities.RequestURL
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ReviewServiceImpl(
        private val authService: AuthServiceImpl,
        private val REVISION_MAPPER: ObjectMapper,
        private val PARTICIPANT: ObjectMapper
) {

    var reviews: MutableList<Review> = mutableListOf()
    var expiredReviewsList: MutableList<Review> = mutableListOf()
    var reviewsWithEmptyRevisionList: MutableList<Review> = mutableListOf()
    var users: MutableMap<String, String> = mutableMapOf()

    /** 2 недели в миллисекундах */
    var defaultTimeToExpired: Long = 1209600000L


    fun getExpiredReviews(filter: String): List<Review> {
        expiredReviewsList.clear()

        val timeToExpired: Long = if (filter == "") defaultTimeToExpired else {
            filter.toLong()*86400000
        }
        val expiredDate = Instant.now().minusMillis(timeToExpired)

        reviews
                .stream()
                .filter { review: Review -> review.state == 1 }
                .filter { review: Review -> Instant.ofEpochMilli(review.getUpdatedAt().toString().toLong()).isBefore(expiredDate) }
                .forEach { review: Review -> expiredReviewsList.add(review) }

        return getParticipantName(expiredReviewsList)
    }

    private fun getReviewsWithEmptyRevision(): List<Review> {
        reviewsWithEmptyRevisionList.clear()
        reviews.stream()
                .filter { review: Review -> review.state == 1 }
                .filter { review: Review -> revisionIsEmpty(review) }
                .forEach { review: Review -> reviewsWithEmptyRevisionList.add(review) }

        return getParticipantName(reviewsWithEmptyRevisionList)
    }

    private fun revisionIsEmpty(review: Review): Boolean {
        val con = authService.getConnection(RequestURL.GET_REVIEWS)
        val jsonRequest = "{\"reviewId\": {\"projectId\": \"elk\", \"reviewId\":\"${review.reviewId.reviewId}\"}}"
        val response = authService.doPostRequestAndReceiveResponse(con, jsonRequest)

        val revisionRootObj = REVISION_MAPPER.readValue(response, ChangesRoot::class.java)
        //у пустых ревью есть аннотация "Review does not contain any revisions."
        return (revisionRootObj.result.getAnnotation() != null)
    }

    private fun getParticipantName(badReviews: MutableList<Review>): List<Review> {
        badReviews
                .forEach { review: Review ->
                    review.participants
                            .forEach { participant: Participant -> participant.name = findUsernameById(participant.userId) }
                }

        badReviews
                .forEach { review: Review ->
                    review.participants.removeIf { participant -> (participant.name == "guest") }
                }
        return badReviews
    }

    private fun findUsernameById(userId: String): String? {
        if (users[userId] != null) return users[userId].toString()

        val con = authService.getConnection(RequestURL.USER_INFO)
        val findUsersRequestDTO = "{\"ids\":\"$userId\"}"

        val resp = authService.doPostRequestAndReceiveResponse(con, findUsersRequestDTO)
        val participant = PARTICIPANT.readValue(resp, ParticipantRoot::class.java)

        users[userId] = participant.result.infos[0].name
        return users[userId]
    }

    fun closeReviews(reviewList: MutableList<Review>) {
        reviewList.forEach { review: Review -> closeReview(review.reviewId.reviewId) }
    }

    fun closeReview(reviewId: String) {
        val con = authService.getConnection(RequestURL.CLOSE_REVIEW)
        val jsonRequest = "{\"reviewId\": {\"projectId\": \"elk\", \"reviewId\":\"${reviewId}\"}, \"isFlagged\":true}"
        authService.doPostRequestAndReceiveResponse(con, jsonRequest)

        reviews.removeIf { r -> r.reviewId.reviewId == reviewId }
        expiredReviewsList.removeIf { r -> r.reviewId.reviewId == reviewId }
        reviewsWithEmptyRevisionList.removeIf { r -> r.reviewId.reviewId == reviewId }

        println("Ревью $reviewId закрыто")
    }
}