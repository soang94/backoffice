package org.example.backoffice.domain.order.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.common.exception.NotHavePermissionException
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
    override fun getProductById(userId: Long): List<OrderResponse> {
        val orders = orderRepository.findAllByUserId(userId)

        return orders.map { order ->
            // 각 주문에 대한 주문 상세 정보를 조회합니다.
            val orderDetails = orderDetailRepository.findAllByOrderId(order.id!!)

            // 주문 상세 정보를 OrderResponse.ProductDetail 객체로 변환합니다.
            val productDetails = orderDetails.map { detail ->
                OrderResponse.ProductDetail(
                    productId = detail.product.id!!,
                    category = detail.product.category.id!!, // 카테고리 ID를 가정
                    productName = detail.product.name,
                    quantity = detail.quantity,
                    pricePerUInt = detail.product.price
                )
            }

            val totalPrice = productDetails.sumOf { it.pricePerUInt * it.quantity }

            OrderResponse(
                id = order.id,
                products = productDetails,
                totalPrice = totalPrice,
                nickname = order.user.nickname, // 유저의 닉네임을 가정
                name = order.user.name,        // 유저의 이름을 가정
                createdAt = order.createdAt
            )
        }
    }

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

    override fun deleteOrder(orderId: Long, userId: Long) {
        val order = orderRepository.findByIdOrNull(orderId) ?: throw ModelNotFoundException("order", orderId)
        if (order.user.id != userId){
            throw NotHavePermissionException(userId, orderId)
        }
        orderRepository.delete(order)
    }
}