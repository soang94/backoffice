package org.example.backoffice.domain.order.service

import org.example.backoffice.domain.order.dto.CreateOrderRequest
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.order.model.Order
import org.example.backoffice.domain.order.model.OrderDetail

interface OrderService {

    fun createOrder(userId: Long): Order
    fun processProductDetails(details: List<CreateOrderRequest.ProductDetail>, userId: Long) : List<OrderDetail>



}