package org.example.backoffice.domain.product.repository

import org.example.backoffice.domain.product.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {

}