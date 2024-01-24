package org.example.backoffice.domain.review.dto

import java.time.LocalDateTime

data class ReviewResponse(
    val id: Long,
    val name: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)