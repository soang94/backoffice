package org.example.backoffice.domain.product.controller

import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.dto.ProductCreateRequest
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.product.service.CategoryService
import org.example.backoffice.domain.user.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/categories")
@RestController
class CategoryController (private val categoryService: CategoryService){

    @GetMapping
    fun getCategory():ResponseEntity<List<CategoryResponse>>{
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory())
    }

    @PostMapping
    fun createCategory (
        @RequestBody categoryCreateRequest: CategoryCreateRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryCreateRequest))
    }

    @PatchMapping("/categoryId")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody categoryCreateRequest: CategoryCreateRequest
    ): ResponseEntity<CategoryResponse>{

        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.updateCategory(categoryId , categoryCreateRequest))
    }
}