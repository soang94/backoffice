package org.example.backoffice.domain.like.service

import org.example.backoffice.domain.like.dto.LikeResponse

interface LikeService {
    fun LikeProduct(productId: Long, userId: Long): LikeResponse
}