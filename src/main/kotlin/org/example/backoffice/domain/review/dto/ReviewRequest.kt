package org.example.backoffice.domain.review.dto


data class ReviewRequest(
    val name: String,
    val content: String,
    val password: String
)