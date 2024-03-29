package org.example.backoffice.domain.order.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.DeleteOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/orders")
@RestController
class OrderController(
    private val orderService: OrderService
) {
    @Operation(summary = "order 조회")
    @GetMapping("/list")
    fun getByUserId(@AuthenticationPrincipal userPrincipal: UserPrincipal):ResponseEntity<List<OrderResponse>>{
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getProductById(userId))
    }
    @Operation(summary = "order 생성")
    @PostMapping()
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest,
                    @AuthenticationPrincipal userPrincipal: UserPrincipal): ResponseEntity<OrderResponse> {
        val userId = userPrincipal.id

        val newOrder= orderService.createOrder(userId)

        val orderDetails = orderService.processProductDetails(createOrderRequest.products, userId,  newOrder.id!!)

        val orderResponse = orderService.createOrderResponseFromOrderDetails(orderDetails)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderResponse)
    }
    @Operation(summary = "order 삭제")
    @DeleteMapping()
    fun deleteOrder (@AuthenticationPrincipal userPrincipal: UserPrincipal, @RequestBody deleteOrderRequest: DeleteOrderRequest
    ) : ResponseEntity<Unit>{
        val userId = userPrincipal.id
        val orderId = deleteOrderRequest.orderId
        orderService.deleteOrder(orderId,userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }



}