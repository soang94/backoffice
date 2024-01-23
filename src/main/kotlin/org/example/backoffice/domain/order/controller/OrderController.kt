package org.example.backoffice.domain.order.controller

import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/orders")
@RestController
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Long): ResponseEntity<OrderResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderService.getOrderById(orderId))
    }

    @GetMapping()
    fun getOrderList() : ResponseEntity<List<OrderResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderService.getAllOrderList())
    }

    @PostMapping
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest)
            : ResponseEntity<OrderResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(orderService.createOrder(createOrderRequest))
    }

    @DeleteMapping("/{orderId}")
    fun deleteOrder(@PathVariable orderId: Long): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}