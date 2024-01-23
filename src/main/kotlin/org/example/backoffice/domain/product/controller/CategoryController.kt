package org.example.backoffice.domain.product.controller

import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.product.service.CategoryService
import org.example.backoffice.domain.user.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController (private val categoryService: CategoryService){
    @PostMapping("/categories")
    fun createCategory (
        @RequestBody categoryCreateRequest: CategoryCreateRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryCreateRequest))
    }
}