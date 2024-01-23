package org.example.backoffice.domain.product.service

import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.product.model.toResponse
import org.example.backoffice.domain.product.repository.CategoryRepository
import org.example.backoffice.domain.user.repository.UserRole
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun createCategory(request: CategoryCreateRequest): CategoryResponse {
        return categoryRepository.save(
            Category(
                name = request.name,
                info = request.info,
                createdAt = request.createdAt
            )
        ).toResponse()

    }


}














