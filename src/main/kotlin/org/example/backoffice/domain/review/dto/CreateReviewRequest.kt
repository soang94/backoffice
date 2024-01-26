package org.example.backoffice.domain.review.dto

data class CreateReviewRequest(
    val name: String,
    val content: String,
)