package org.example.backoffice.domain.like.service

import jakarta.transaction.Transactional
import org.example.backoffice.common.exception.LikeException
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.like.model.Like
import org.example.backoffice.domain.like.model.toResponse
import org.example.backoffice.domain.like.repository.LikeRepository
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.product.model.toResponse
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
):LikeService {
    @Transactional
    override fun LikeProduct(productId: Long, userId: Long): LikeResponse {
        //변수 선언
        val product: Product =
            productRepository.findByIdOrNull(productId) ?: throw ModelNotFoundException("Product", productId)
        val user: User = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        var existingLike = likeRepository.findByProductIdAndUserId(productId, userId)
        val productUserId = product.user.id
        //postId와 userId에 대해서 null 일때 newLike 로 좋아요 생성 , 그 후 countLikes를 플러스 해서 저장 liked 상태는 true
//        return if (existingLike = null) {
        val newLike = likeRepository.save(Like(product = product, user = user, likes = true))
        if (productUserId != userId) {
            product.countLiked++
            productRepository.save(product)
            newLike.toResponse()
            //같을 경우 Exception
        } else {
            throw LikeException(productId)
        }

//        if(!likeRepository.existsLikeByProduct(product)){
//            product.countLiked+1
//            productRepository.save(product)
//        }else {
//            product.countLiked-1
//            likeRepository.deleteLikeByProduct(product)
//        }
        //null 이 아닐시(존재할 경우), 삭제후 countLikes를 마이너스 해서 저장 liked 상태를 false 로
//        } else {
//            likeRepository.delete(existingLike)
//            product.countLiked--
//            productRepository.save(product)
//            existingLike.toResponse().copy(liked = false)
//        }
        return newLike.toResponse()
    }
}


