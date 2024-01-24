package org.example.backoffice.domain.product.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.product.model.toResponse
import org.example.backoffice.domain.product.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {
    override fun createCategory(request: CategoryCreateRequest): CategoryResponse {
        return categoryRepository.save(
            Category(
                name = request.name!!,
                info = request.info!!
            )
        ).toResponse()

    }

    override fun getCategory(): List<CategoryResponse> {
        val categories = categoryRepository.findAll()
        return categories.map { category ->
            CategoryResponse(
                id = category.id,
                name = category.name,
                info = category.info,
                createdAt = category.createdAt
            )
        }
    }

    override fun updateCategory(categoryId: Long, request: CategoryCreateRequest): CategoryResponse {
        val category =
            categoryRepository.findByIdOrNull(categoryId) ?: throw ModelNotFoundException("category", categoryId)

        category.name = request.name ?: category.name
        category.info = request.info ?: category.name
        val updateCategory = categoryRepository.save(category)

        return updateCategory.toResponse()
    }

}














