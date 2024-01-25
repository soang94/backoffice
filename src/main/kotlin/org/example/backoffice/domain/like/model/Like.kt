package org.example.backoffice.domain.like.model

import jakarta.persistence.*
import org.example.backoffice.domain.like.dto.LikeResponse
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.user.model.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "like")
class Like (
    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product?,

    @Column(name = "likes")
    var likes: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Like.toResponse(): LikeResponse {
    return LikeResponse(
        userId = user.id!!,
        likes = likes,
    )
}