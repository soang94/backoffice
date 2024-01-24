package org.example.backoffice.domain.product.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.product.model.toResponse
import org.example.backoffice.domain.product.repository.CategoryRepository
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.user.model.User
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.nio.file.AccessDeniedException


@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ProductService {
    override fun getProduct(): List<ProductResponse> {
        val products = productRepository.findAll()
        return products.map { product ->
            ProductResponse(
                id = product.id,
                userId = product.user.id,
                name = product.name,
                price = product.price,
                title = product.title,
                info = product.info,
                categoryId = product.category.id!!,
                createdAt = product.createdAt,
                updatedAt = product.updatedAt
            )
        }

    }

    override fun getProductById(productId: Long): ProductResponse {
        val product =
            productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        return product.toResponse()
    }

    override fun createProduct(request: ProductCreateRequest, userId: Long): ProductResponse {
        val user: User = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val category: Category = categoryRepository.findByIdOrNull(request.categoryId) ?: throw ModelNotFoundException("category", request.categoryId)
        val createdProduct = productRepository.save(
            Product(
                user = user,
                name = request.name!!,
                price = request.price!!,
                title = request.title!!,
                info = request.info!!,
                category = category
            )
        )
        return createdProduct.toResponse()
    }

    override fun updateProduct(productId: Long, userId: Long, request: ProductCreateRequest): ProductResponse {
        val product =
            productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("product", productId)
        val category = categoryRepository.findByIdOrNull(request.categoryId)
        if (product.user.id != userId) {
            throw AccessDeniedException("User with ID $userId does not have permission to update post with ID $productId")
        }
        product.name = request.name ?: product.name
        product.price =request.price ?: product.price
        product.title = request.title ?: product.title
        product.info = request.info ?: product.info
        product.category = category ?: product.category
        val updateProduct = productRepository.save(product)

        return updateProduct.toResponse()
    }

    override fun deleteProduct(productId: Long, userId: Long) {
        val product = productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("product", productId)
        if (product.user.id != userId) {
            throw AccessDeniedException("User with ID $userId does not have permission to update post with ID $productId")
        }
        productRepository.delete(product)
    }
}