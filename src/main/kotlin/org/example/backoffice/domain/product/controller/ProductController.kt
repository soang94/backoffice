package org.example.backoffice.domain.product.controller

import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.product.service.ProductService
import org.example.backoffice.domain.user.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
@RestController
class ProductController (private val productService: ProductService){
    @GetMapping
    fun getProduct(): ResponseEntity<List<ProductResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct())
    }

    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long):ResponseEntity<ProductResponse>{
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId))
    }

    @PostMapping("/new")
    fun createProduct (@AuthenticationPrincipal userPrincipal: UserPrincipal ,@RequestBody productCreateRequest: ProductCreateRequest
    ): ResponseEntity<ProductResponse>{
        val userId =  userPrincipal.id
        val productResponse = productService.createProduct(productCreateRequest, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse)
    }

    @PatchMapping("/{productId}")
    fun updateProduct (@AuthenticationPrincipal userPrincipal: UserPrincipal, @PathVariable productId: Long, @RequestBody productCreateRequest: ProductCreateRequest
    ): ResponseEntity<ProductResponse>{
        val userId =  userPrincipal.id
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productService.updateProduct(productId, userId, productCreateRequest))
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct (@AuthenticationPrincipal userPrincipal: UserPrincipal, @PathVariable productId: Long):ResponseEntity<Unit> {
        val userId =  userPrincipal.id
        productService.deleteProduct(productId, userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}