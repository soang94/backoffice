package org.example.backoffice.domain.like.controller

import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.like.service.LikeService
import org.example.backoffice.domain.product.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/likes")
@RestController
class LikeController(
    private val productService: ProductService,
    private val likeService: LikeService,
) {
    @PatchMapping("/{productId}")
    fun LikeProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable productId: Long
    ): ResponseEntity<LikeResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(likeService.LikeProduct(productId, userId))
    }
}