package org.example.backoffice.domain.order.service

import jakarta.transaction.Transactional
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.repository.OrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository
) : OrderService {
    override fun getAllOrderList(): List<OrderResponse> {
        val orders = orderRepository.findAll()
        return orders.map { order ->
            OrderResponse(
                id = order.id!!,
                productId = order.product.id,
                category = order.product.category.name,
                price = order.product.price,
                nickname = order.user.nickname,
                name = order.user.name,
                createdAt= order.createdAt,
            )
        }
    }

    override fun getOrderById(orderId: Long): OrderResponse {
        val order =
            orderRepository.findByIdOrNull(orderId) ?: throw ModelNotFoundException("Order", orderId)
        return OrderResponse(
            id = order.id!!,
            productId = order.product.id,
            category = order.product.category.name,
            price = order.product.price,
            nickname = order.user.nickname,
            name = order.user.name,
            createdAt= order.createdAt,
        )
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