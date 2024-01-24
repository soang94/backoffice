package org.example.backoffice.domain.order.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
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


    ) : BaseTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
