package org.example.backoffice.domain.review.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.review.dto.DeleteReviewRequest
import org.example.backoffice.domain.review.dto.ReviewRequest
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.example.backoffice.domain.review.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RequestMapping("/products/{productId}/reviews")
@RestController
class ReviewController(
    private val reviewService: ReviewService
) {

    @Operation(summary = "review 단건 조회")
    @GetMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReview(productId, reviewId))
    }

    @Operation(summary = "review 작성")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun createReview(
        @PathVariable productId: Long,
        @RequestBody request: ReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(productId, request))
    }


    @Operation(summary = "review 수정")
    @PatchMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun updateReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
        @RequestBody request: ReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(productId, reviewId, request))
    }

    @Operation(summary = "review 삭제")
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun deleteReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
        @RequestBody deleteReviewRequest: DeleteReviewRequest
    ): ResponseEntity<Unit> {
        reviewService.deleteReview(productId, reviewId, deleteReviewRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}




