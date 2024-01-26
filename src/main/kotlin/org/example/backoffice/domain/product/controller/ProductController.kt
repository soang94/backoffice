package org.example.backoffice.domain.product.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.product.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
@RestController
class ProductController(private val productService: ProductService) {


    @Operation(summary = "product 전체 조회")
    @GetMapping
    fun getProduct(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct())
    }

    @Operation(summary = "product 단건 조회")
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<ProductResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId))
    }

    @Operation(summary = "product 작성")
    @PostMapping
    fun createProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal, @RequestBody productCreateRequest: ProductCreateRequest
    ): ResponseEntity<ProductResponse> {
        val userId = userPrincipal.id
        val productResponse = productService.createProduct(productCreateRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse)
    }

    @Operation(summary = "product 수정")
    @PatchMapping("/{productId}")
    fun updateProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable productId: Long,
        @RequestBody productCreateRequest: ProductCreateRequest
    ): ResponseEntity<ProductResponse> {
        val userId = userPrincipal.id
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productService.updateProduct(productId, userId, productCreateRequest))
    }

    @Operation(summary = "product 삭제")
    @DeleteMapping("/{productId}")
    fun deleteProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable productId: Long
    ): ResponseEntity<Unit> {
        val userId = userPrincipal.id
        productService.deleteProduct(productId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}