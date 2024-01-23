package org.example.backoffice.domain.product.repository

import org.example.backoffice.domain.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByProductIdOrNull(productId : Long) : Product ?
}
