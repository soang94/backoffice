package org.example.backoffice.domain.order.controller

import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.model.OrderDetail
import org.example.backoffice.domain.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/orders")
@RestController
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping()
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest,
                    @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<OrderResponse> {
        val userId = userPrincipal.id

        // 주문 생성
        orderService.createOrder(userId)

        // 주문 상세 정보 처리
        val orderDetails = orderService.processProductDetails(createOrderRequest.products, userId)

        // 주문 응답 생성
        val orderResponse = orderDetails.toOrderResponse()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderResponse)
    }

    // 주문을 OrderResponse로 변환하는 로컬 함수
    private fun OrderDetail.toOrderResponse(): OrderResponse {
        val productDetails = this.orderDetails.map { detail ->
            OrderResponse.ProductDetail(
                productId = detail.product.id,
                category = detail.product.categoryId,
                productName = detail.product.name,
                quantity = detail.quantity,
                pricePerUInt =  detail.product.price
            )
        }

        return OrderResponse(
            id = this.id,
            products = productDetails,
            totalPrice = productDetails.sumOf { it.pricePerUnit * it.quantity },
            nickname = this.order.user.nickname,
            name = this.order.user.name,
            createdAt = this.createdAt
        )
    }
}