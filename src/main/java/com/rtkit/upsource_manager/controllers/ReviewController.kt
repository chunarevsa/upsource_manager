package com.rtkit.upsource_manager.controllers

import com.rtkit.upsource_manager.payload.pacer.review.Review
import com.rtkit.upsource_manager.services.AuthServiceImpl
import com.rtkit.upsource_manager.services.ReviewServiceImpl

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/main/review")
class ReviewController(
        private val reviewService: ReviewServiceImpl,
        private val authService: AuthServiceImpl,
) {

    @GetMapping
    fun getReviewsFromClose(model: Model, @RequestParam(required = false, defaultValue = "") filter : String ): String {

        if (!authService.isAuthenticated && !authService.validateCon())
            return "redirect:/login"

        val expiredReviews: Iterable<Review> = reviewService.getExpiredReviews(filter)
        val reviewsWithEmptyRevision: Iterable<Review> = reviewService.reviewsWithEmptyRevisionList

        model.addAttribute("expiredReviews", expiredReviews)
        model.addAttribute("reviewsWithEmptyRevision", reviewsWithEmptyRevision)
        model.addAttribute("filter", filter)
        return "reviews"
    }

    @PostMapping
    @RequestMapping("/closeAll")
    fun closeReviews(model: Model): String {
        reviewService.closeReviews(reviewService.expiredReviewsList)
        reviewService.closeReviews(reviewService.reviewsWithEmptyRevisionList)
        return "redirect:/main/review"
    }

    @PostMapping
    @RequestMapping("/close/{reviewId}")
    fun closeReview(model: Model, @PathVariable reviewId: String): String {
        reviewService.closeReview(reviewId)
        return "redirect:/main/review"
    }
}