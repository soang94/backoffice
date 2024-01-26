package org.example.backoffice.domain.order.service

import jakarta.transaction.Transactional
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.order.dto.CreateOrderRequest
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


    override fun processProductDetails(details: List<CreateOrderRequest.ProductDetail>, userId: Long): List<OrderDetail> {
        val productIds = details.map { it.productId }
        val products = productRepository.findAllById(productIds)
        val latestOrder = orderRepository.findTopByUserIdOrderByOrderIdDesc(userId)
            ?: throw IllegalStateException("No orders found for user with ID $userId")

        val productMap = products.associateBy { it.id }

        return details.map { detail ->
            // Map에서 상품을 조회합니다.
            val product = productMap[detail.productId]
            if (product != null) {
                // 상품이 존재하면 OrderDetail 객체를 생성합니다.
                OrderDetail(
                    product = product,
                    quantity = detail.quantity,
                    order = latestOrder
                )
            } else {
                // 상품이 존재하지 않을 경우 예외를 발생시킵니다.
                throw IllegalArgumentException("Product with ID ${detail.productId} not found")
            }
        }
    }
}