package org.example.backoffice.domain.like.repository

import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.like.model.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun findByProductIdAndUserId(productId: Long, userId: Long): Like?

    fun findAllByUserId(userId: Long): List<LikeResponse>
    fun countByProductId(productId: Long): Long
}