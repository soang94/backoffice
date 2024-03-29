package org.example.backoffice.domain.like.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.like.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/likes")
@RestController
class LikeController(
    private val likeService: LikeService,
) {
    @Operation(summary = "찜 하기")
    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun likeProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @PathVariable productId: Long
    ): ResponseEntity<LikeResponse> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(likeService.likeProduct(productId, userId))
    }

    @Operation(summary = "찜 하기 조회")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun likeCheckProduct(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<LikeResponse>> {
        val userId = userPrincipal.id
        return ResponseEntity.status(HttpStatus.OK).body(likeService.likeCheckProduct(userId))
    }
}