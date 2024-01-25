package org.example.backoffice.domain.like.repository

import org.example.backoffice.domain.like.model.Like
import org.example.backoffice.domain.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository:JpaRepository<Like, Long> {
    fun findByProductIdAndUserId(productId:Long, userId:Long)

    fun countByProductId(productId: Long): Long

    fun existsLikeByProduct(product: Product):Boolean

    fun deleteLikeByProduct(product: Product)
}