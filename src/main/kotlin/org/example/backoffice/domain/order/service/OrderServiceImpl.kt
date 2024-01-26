package org.example.backoffice.domain.order.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.model.Order
import org.example.backoffice.domain.order.model.OrderDetail
import org.example.backoffice.domain.order.repository.OrderRepository
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : OrderService {
    override fun createOrder(userId: Long): Order {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        // 주문을 생성하고 저장합니다.
        val order = Order(
            user = user,
            // 추가적인 주문 관련 정보를 설정할 수 있습니다.
        )
        return orderRepository.save(order)
    }


    override fun processProductDetails(details: List<CreateOrderRequest.ProductDetail>, userId: Long, newOrderId: Long): List<OrderDetail> {
        val productIds = details.map { it.productId }
        val products = productRepository.findAllById(productIds)
        val order = orderRepository.findByIdOrNull(newOrderId)?: throw ModelNotFoundException("order", newOrderId)

        val productMap = products.associateBy { it.id }

        return details.map { detail ->
            // Map에서 상품을 조회합니다.
            val product = productMap[detail.productId]
            if (product != null) {
                // 상품이 존재하면 OrderDetail 객체를 생성합니다.
                OrderDetail(
                    product = product,
                    quantity = detail.quantity,
                    order = order
                )
            } else {
                // 상품이 존재하지 않을 경우 예외를 발생시킵니다.
                throw IllegalArgumentException("Product with ID ${detail.productId} not found")
            }
        }
    }

    override fun createOrderResponseFromOrderDetails(orderDetails: List<OrderDetail>): OrderResponse {
        // Assuming that all OrderDetail objects belong to the same Order.
        val orderId = orderDetails.first().order.id
        val productDetails = orderDetails.map { detail ->
            OrderResponse.ProductDetail(
                productId = detail.product.id!!,
                category = detail.product.category.id!!, // Assuming you have a 'category' field.
                productName = detail.product.name,
                quantity = detail.quantity,
                pricePerUInt = detail.product.price
            )
        }

        // Calculate the total price
        val totalPrice = productDetails.sumOf { it.pricePerUInt * it.quantity }

        // Assuming all OrderDetail objects have the same order, so we can take the user from the first.
        val nickname = orderDetails.first().order.user.nickname // Assuming there is a 'nickname' field in the User model.
        val name = orderDetails.first().order.user.name // Assuming there is a 'name' field in the User model.
        val createdAt = orderDetails.first().order.createdAt // Assuming there is a 'createdAt' field in the Order model.

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