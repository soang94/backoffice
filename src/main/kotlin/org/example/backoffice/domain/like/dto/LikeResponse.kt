package org.example.backoffice.domain.like.dto

data class LikeResponse(
    val userId: Long,
    val productId: Long,
    val likes: Boolean
)