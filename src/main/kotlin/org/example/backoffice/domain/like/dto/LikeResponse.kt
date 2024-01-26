package org.example.backoffice.domain.like.dto

data class LikeResponse(
    val userId: Long,
    val likes: Boolean
)