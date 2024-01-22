package org.example.backoffice.domain.comment.dto

import java.time.LocalDateTime

data class CommentsResponse(
    val id: Long,
    val name: String,
    val content: String,
    val createdAt: LocalDateTime
)