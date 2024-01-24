package org.example.backoffice.domain.order.dto

data class CreateOrderRequest(
    val quantity: Int,
    val productId : Long
    // val title: String,
    // val nickname: String,
    // val name: String
)

