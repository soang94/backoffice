package org.example.backoffice.domain.order.dto

import java.time.LocalDateTime

data class OrderResponse(
    val productId: Long,
    // val memberId: Long,
    val id: Long,
    val title: String,
    val category: String,
    val price: Long,
    val nickname: String,
    val name: String,
    val createdAt: LocalDateTime,
    val updateAt: LocalDateTime
)