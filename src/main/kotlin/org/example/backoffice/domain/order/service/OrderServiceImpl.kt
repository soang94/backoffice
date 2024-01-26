package org.example.backoffice.domain.order.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.model.Order
import org.example.backoffice.domain.order.model.OrderDetail
import org.example.backoffice.domain.order.repository.OrderDetailRepository
import org.example.backoffice.domain.order.repository.OrderRepository
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val orderDetailRepository: OrderDetailRepository,
) : OrderService {
    override fun createOrder(userId: Long): Order {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        val order = Order(
            user = user,
        )
        return orderRepository.save(order)
    }


    override fun processProductDetails(details: List<CreateOrderRequest.ProductDetail>, userId: Long, newOrderId: Long): List<OrderDetail> {
        val productIds = details.map { it.productId }
        val products = productRepository.findAllById(productIds)
        val order = orderRepository.findByIdOrNull(newOrderId)?: throw ModelNotFoundException("order", newOrderId)

        val productMap = products.associateBy { it.id }

        return details.map { detail ->
            val product = productMap[detail.productId]
            if (product != null) {
                val orderDetail = OrderDetail(
                    product = product,
                    quantity = detail.quantity,
                    order = order
                )
                orderDetailRepository.save(orderDetail)
            } else {
                throw IllegalArgumentException("Product with ID ${detail.productId} not found")
            }
        }
    }

    override fun createOrderResponseFromOrderDetails(orderDetails: List<OrderDetail>): OrderResponse {
        val orderId = orderDetails.first().order.id
        val productDetails = orderDetails.map { detail ->
            OrderResponse.ProductDetail(
                productId = detail.product.id!!,
                category = detail.product.category.id!!,
                productName = detail.product.name,
                quantity = detail.quantity,
                pricePerUInt = detail.product.price
            )
        }

        val totalPrice = productDetails.sumOf { it.pricePerUInt * it.quantity }

        val nickname = orderDetails.first().order.user.nickname
        val name = orderDetails.first().order.user.name
        val createdAt = orderDetails.first().order.createdAt

        return OrderResponse(
            id = orderId,
            products = productDetails,
            totalPrice = totalPrice,
            nickname = nickname,
            name = name,
            createdAt = createdAt
        )
    }
}