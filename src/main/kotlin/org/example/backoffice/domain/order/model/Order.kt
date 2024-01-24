package org.example.backoffice.domain.order.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.user.model.User

@Entity
@Table(name = "orders")

class Order(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column
    val quantity: Int


    ) : BaseTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Order.toResponse(): OrderResponse{
    return OrderResponse(
        category = product.category.name,
        createdAt = this.createdAt,
        id = id,
        name = user.name,
        nickname = user.nickname,
        price = product.price,
        productId = product.id,
        quantity = quantity
    )
}