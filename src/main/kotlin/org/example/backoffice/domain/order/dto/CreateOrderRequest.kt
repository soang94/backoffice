package org.example.backoffice.domain.order.dto

data class CreateOrderRequest(
    val productId: Long,
    // val memberId: Long,
    val title: String,
    val nickname: String,
    val name: String,
)

