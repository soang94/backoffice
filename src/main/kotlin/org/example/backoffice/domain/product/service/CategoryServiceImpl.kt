package org.example.backoffice.domain.product.service

import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.product.model.toResponse
import org.example.backoffice.domain.product.repository.CategoryRepository
import org.example.backoffice.domain.user.repository.UserRole

class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun createCategory(request: CategoryCreateRequest, userRole: String): CategoryResponse {
        if (UserRole.valueOf(userRole) == UserRole.ADMIN) error("권한이 없습니다.")
//        val createdCategory= categoryRepository.save(
//            Category(
//                name = name,
//            )
//        )
//        return createdCategory.toResponse()
        TODO()
    }
}














