package org.example.backoffice.domain.review.controller

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

    //댓글 단건 조회
    @GetMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getReview(productId, reviewId))
    }

    //댓글 작성
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun createReview(
        @PathVariable productId: Long,
        @RequestBody request: ReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(productId, request))
    }


    //댓글 수정
    @PatchMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    fun updateReview(
        @PathVariable productId: Long,
        @PathVariable reviewId: Long,
        @RequestBody request: ReviewRequest
    ): ResponseEntity<ReviewResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(productId, reviewId, request))
    }

    //댓글 삭제
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




