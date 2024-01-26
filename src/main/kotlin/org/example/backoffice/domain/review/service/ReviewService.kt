package org.example.backoffice.domain.review.service

import org.example.backoffice.domain.review.dto.CreateReviewRequest
import org.example.backoffice.domain.review.dto.ReviewRequest
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.example.backoffice.domain.review.dto.DeleteReviewRequest

interface ReviewService {

    fun getReview(productId: Long, reviewId: Long): ReviewResponse

    fun createReview(productId: Long, request: CreateReviewRequest): ReviewResponse

    fun updateReview(productId: Long, reviewId: Long, request: ReviewRequest, userId: Long): ReviewResponse

    fun deleteReview(productId: Long, reviewId: Long, request: DeleteReviewRequest, userId: Long)
}