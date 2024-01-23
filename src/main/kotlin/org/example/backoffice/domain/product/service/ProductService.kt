package org.example.backoffice.domain.product.service

import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse

interface ProductService {
    fun getProduct(): List<ProductResponse>

    fun getProductById(productId: Long): ProductResponse

    fun createProduct(request: ProductCreateRequest, userId: Long): ProductResponse

    fun updateProduct(productId: Long, userId: Long, request: ProductCreateRequest): ProductResponse

    fun deleteProduct(productId: Long, userId: Long)
}
