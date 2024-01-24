package org.example.backoffice.domain.order.dto

import java.time.LocalDateTime

data class OrderResponse(
    val id: Long?,
    val productId: Long?,
    val category: String?,
    val price: Long,
    val nickname: String,
    val name: String,
    val createdAt: LocalDateTime
)