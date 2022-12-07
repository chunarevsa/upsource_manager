package com.rtkit.upsource_manager.repositories

import com.rtkit.upsource_manager.entities.review.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository :  JpaRepository<ReviewEntity, Long> {
}