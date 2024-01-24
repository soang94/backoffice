package org.example.backoffice.domain.product.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.product.dto.ProductResponse
import org.example.backoffice.domain.review.model.Review
import org.example.backoffice.domain.review.model.toResponse
import org.example.backoffice.domain.user.model.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "product")
class Product (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(name = "name") var name: String,

    @Column(name = "price") var price: Long,

    @Column(name = "title") var title: String,

    @Column(name = "info") var info: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    var category: Category,

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val review: MutableList<Review> = mutableListOf()


): BaseTime(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
//
//    fun addReview(review: Review) {
//        review.add(review)
//    }
}

fun Product.toResponse(): ProductResponse{
    return ProductResponse(
        id = id,
        userId = user.id!!,
        name = name,
        price = price,
        title = title,
        info = info,
        categoryId = category.id!!,
        review = review.map { it.toResponse() },
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
