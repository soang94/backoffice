package org.example.backoffice.domain.order.repository


import org.example.backoffice.domain.order.model.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository : JpaRepository<OrderDetail, Long> {
}