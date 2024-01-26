package org.example.backoffice.domain.order.dto

import org.example.backoffice.domain.product.service.ProductService
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long?,
    val products: List<ProductDetail>,
    val totalPrice: Long,
    val nickname: String,
    val name: String,
    val createdAt: LocalDateTime
){
    data class ProductDetail(
        val productId: Long,
        val category: Long,
        val productName: String,
        val quantity: Int,
        val pricePerUInt: Long,
    )
}