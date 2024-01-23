package org.example.backoffice.domain.order.service

import jakarta.transaction.Transactional
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl: OrderService {
    override fun getAllOrderList(): List<OrderResponse> {
        TODO()
    }

    override fun getOrderById(orderId: Long): OrderResponse {
        TODO()
    }

    @Transactional
    override fun createOrder(request: CreateOrderRequest): OrderResponse {
        TODO()
    }

    @Transactional
    override fun deleteOrder(orderId: Long) {
        TODO()
    }
}