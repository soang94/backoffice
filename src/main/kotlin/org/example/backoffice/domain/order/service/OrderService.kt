package org.example.backoffice.domain.order.service

import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse

interface OrderService {
    fun getAllOrderList(): List<OrderResponse>

    fun getOrderById(orderId: Long): OrderResponse

    fun createOrder(productId: Long, request: CreateOrderRequest, userId: Long): OrderResponse

    fun deleteOrder(orderId: Long, userId: Long)
}