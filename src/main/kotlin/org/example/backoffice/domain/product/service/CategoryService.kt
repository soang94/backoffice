package org.example.backoffice.domain.product.service

import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse

interface CategoryService {
    fun createCategory(request : CategoryCreateRequest) : CategoryResponse

    fun getCategory() : List<CategoryResponse>

    fun updateCategory(categoryId : Long, request: CategoryCreateRequest ) : CategoryResponse
}