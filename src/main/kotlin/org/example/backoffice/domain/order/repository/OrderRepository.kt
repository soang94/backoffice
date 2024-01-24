package org.example.backoffice.domain.order.repository

import org.example.backoffice.domain.order.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>{
}