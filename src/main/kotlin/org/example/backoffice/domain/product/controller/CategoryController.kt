package org.example.backoffice.domain.product.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.domain.product.dto.CategoryCreateRequest
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/categories")
@RestController
class CategoryController(private val categoryService: CategoryService) {

    @Operation(summary = "category 전체 조회")
    @GetMapping
    fun getCategory(): ResponseEntity<List<CategoryResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory())
    }

    @Operation(summary = "category 생성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun createCategory(
        @RequestBody categoryCreateRequest: CategoryCreateRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryCreateRequest))
    }

    @Operation(summary = "category 수정")
    @PatchMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCategory(
        @PathVariable categoryId: Long,
        @RequestBody categoryCreateRequest: CategoryCreateRequest
    ): ResponseEntity<CategoryResponse> {

        return ResponseEntity.status(HttpStatus.OK)
            .body(categoryService.updateCategory(categoryId, categoryCreateRequest))
    }
}