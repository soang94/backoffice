package org.example.backoffice.domain.product.repository

import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Product, Long> {
    fun findByCategoryIdOrNull(categoryId : Long) : Category?
}