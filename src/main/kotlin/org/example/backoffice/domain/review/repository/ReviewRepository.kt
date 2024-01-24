package org.example.backoffice.domain.review.repository

import org.example.backoffice.domain.review.model.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByProductId(productId: Long): List<Review>
}