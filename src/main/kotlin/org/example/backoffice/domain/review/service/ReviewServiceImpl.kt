package org.example.backoffice.domain.review.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.review.dto.ReviewRequest
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.example.backoffice.domain.review.dto.DeleteReviewRequest
import org.example.backoffice.domain.review.model.Review
import org.example.backoffice.domain.review.model.toResponse
import org.example.backoffice.domain.review.repository.ReviewRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val productRepository: ProductRepository
) : ReviewService {

    //댓글 단건 조회
    override fun getReview(productId: Long, reviewId: Long): ReviewResponse {
        val review =
            reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시시도해주세요")
        return review.toResponse()
    }


    //댓글 작성
    override fun createReview(productId: Long, request: ReviewRequest): ReviewResponse {
        val product = productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val createReview = reviewRepository.save(
            Review(
                name = request.name!!,
                content = request.content!!,
                password = request.password,
                product = product
            )
        )
        return createReview.toResponse()
    }

    //댓글 수정
    @Transactional
    override fun updateReview(productId: Long, reviewId: Long, request: ReviewRequest): ReviewResponse {
        val review =
            reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시 시도해주세요")


        if (review.password != request.password)
            throw IllegalStateException("맞지 않는 비밀번호입니다. 다시 시도해주세요")
        else {
            review.name = request.name ?: review.name
            review.content = request.content ?: review.content
            reviewRepository.save(review)

            return review.toResponse()
        }
    }

    //댓글 삭제
    @Transactional
    override fun deleteReview(productId: Long, reviewId: Long, request: DeleteReviewRequest) {
        val product =
            productRepository.findByIdOrNull(productId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시시도해주세요")
        val review =
            reviewRepository.findByIdOrNull(reviewId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시시도해주세요")

        if (review.password != request.password)
            throw IllegalStateException("맞지 않는 비밀번호입니다. 다시 시도해주세요")
        else {
            reviewRepository.delete(review)
            reviewRepository.save(review)
        }

    }
}