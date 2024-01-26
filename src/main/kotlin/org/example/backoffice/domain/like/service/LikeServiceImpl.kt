package org.example.backoffice.domain.like.service

import jakarta.transaction.Transactional
import org.example.backoffice.common.exception.LikeException
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.like.model.Like
import org.example.backoffice.domain.like.model.toResponse
import org.example.backoffice.domain.like.repository.LikeRepository
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.product.repository.ProductRepository
import org.example.backoffice.domain.user.model.User
import org.example.backoffice.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
) : LikeService {
    @Transactional
    override fun likeProduct(productId: Long, userId: Long): LikeResponse {
        val product: Product =
            productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val user: User = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        val existingLike = likeRepository.findByProductIdAndUserId(productId, userId)
        val productUserId = product.user.id
        return if (existingLike == null) {
            val liking = likeRepository.save(Like(product = product, user = user, likes = true))
            if (productUserId != userId) {
                product.countLiked++
                productRepository.save(product)
                liking.toResponse()
            } else {
                throw LikeException(productId, userId)
            }
        } else {
            likeRepository.delete(existingLike)
            product.countLiked--
            productRepository.save(product)
            existingLike.toResponse().copy(likes = false)
        }
    }
}

