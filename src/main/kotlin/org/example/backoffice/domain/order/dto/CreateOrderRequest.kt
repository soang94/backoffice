package org.example.backoffice.domain.order.dto

data class CreateOrderRequest(
    val products: List<ProductDetail>
) {
    data class ProductDetail(
        val productId: Long,
        val quantity: Int
    )
}

