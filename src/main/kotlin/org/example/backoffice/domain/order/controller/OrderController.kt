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
        val newOrder= orderService.createOrder(userId)

        // 주문 상세 정보 처리
        val orderDetails = orderService.processProductDetails(createOrderRequest.products,userId,  newOrder.id!!)

        // 주문 응답 생성
        val orderResponse = orderService.createOrderResponseFromOrderDetails(orderDetails)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderResponse)
    }

}