package org.example.backoffice.domain.review.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
@Table(name = "reviews")
class Review(
    @Column(name = "name") var name: String,
    @Column(name = "content") var content: String,
    @Column(name = "password") var password: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var product: Product
):BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Review.toResponse(): ReviewResponse {
    return ReviewResponse(
        id = id!!,
        name = name,
        content = content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}