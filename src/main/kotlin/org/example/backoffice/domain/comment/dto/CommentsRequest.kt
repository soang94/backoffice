package org.example.backoffice.domain.comment.dto

import java.time.LocalDateTime

data class CommentsRequest(
    val name: String,
    val content: String,
    val password: String,
    val createdAt: LocalDateTime
)