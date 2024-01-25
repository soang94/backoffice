package org.example.backoffice.domain.order.service

import jakarta.transaction.Transactional
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.common.exception.NotHavePermissionException
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.model.Order
import org.example.backoffice.domain.order.model.toResponse
import org.example.backoffice.domain.order.repository.OrderRepository
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.nio.file.AccessDeniedException

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
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
                createdAt = order.createdAt,
                quantity = order.quantity
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
            createdAt = order.createdAt,
            quantity = order.quantity
        )
    }

    override fun createOrder(productId: Long, request: CreateOrderRequest, userId: Long):
            OrderResponse {
        val product = productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val orders = orderRepository.save(
            Order(
                product = product,
                user = user,
                quantity = request.quantity
            )
        )
        return orders.toResponse()
    }


    @Transactional
    override fun deleteOrder(orderId: Long, userId: Long) {
        val order = orderRepository.findByIdOrNull(orderId) ?: throw ModelNotFoundException("Order", orderId)
        if (order.user.id != userId) {
            throw NotHavePermissionException(userId, orderId)
        }
        orderRepository.delete(order)
    }
}