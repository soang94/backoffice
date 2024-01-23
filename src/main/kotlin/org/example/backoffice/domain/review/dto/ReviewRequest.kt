package org.example.backoffice.domain.review.dto

import java.time.LocalDateTime

data class ReviewRequest(
    val name: String,
    val content: String,
    val password: String
)