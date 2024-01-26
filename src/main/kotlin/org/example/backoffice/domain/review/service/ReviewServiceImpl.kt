package org.example.backoffice.domain.review.service

import org.example.backoffice.common.exception.InvalidPasswordException
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.review.dto.CreateReviewRequest
import org.example.backoffice.domain.review.dto.ReviewRequest
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.example.backoffice.domain.review.dto.DeleteReviewRequest
import org.example.backoffice.domain.review.model.Review
import org.example.backoffice.domain.review.model.toResponse
import org.example.backoffice.domain.review.repository.ReviewRepository
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ReviewService {

    //댓글 단건 조회
    override fun getReview(productId: Long, reviewId: Long): ReviewResponse {
        val review =
            reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        return review.toResponse()
    }


    //댓글 작성
    override fun createReview(productId: Long, request: CreateReviewRequest): ReviewResponse {
        val product = productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val createReview = reviewRepository.save(
            Review(
                name = request.name,
                content = request.content,
                product = product,
            )
        )
        return createReview.toResponse()
    }

    //댓글 수정
    @Transactional
    override fun updateReview(productId: Long, reviewId: Long, request: ReviewRequest, userId: Long): ReviewResponse {
        val review =
            reviewRepository.findByProductIdAndId(productId ,reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if (user.password != request.password)
            throw InvalidPasswordException(request.password)
        else {
            review.name = request.name ?: review.name
            review.content = request.content ?: review.content
            reviewRepository.save(review)

            return review.toResponse()
        }
    }

    //댓글 삭제
    @Transactional
    override fun deleteReview(productId: Long, reviewId: Long, request: DeleteReviewRequest, userId: Long) {
        val product =
            productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val review =
            reviewRepository.findByIdOrNull(reviewId) ?: throw ModelNotFoundException("Review", reviewId)
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if (user.password != request.password)
            throw InvalidPasswordException(request.password)
        else {
            product.removeReview(review)
            reviewRepository.save(review)
        }

    }
}