package org.example.backoffice.domain.order.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.order.dto.OrderResponse
import org.example.backoffice.domain.product.model.Product
import org.example.backoffice.domain.user.model.User

@Entity
@Table(name = "orders")

class Order(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,


    ) : BaseTime() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
//fun Order.toOrderResponse(): OrderResponse {
//    val productDetails = this.orderDetails.map { detail ->
//        OrderResponse.ProductDetail(
//            productId = detail.product.id,
//            category = detail.product.categoryId, // Assuming there is a categoryId in the Product
//            productName = detail.product.name,
//            quantity = detail.quantity,
//            pricePerUInt = detail.product.price
//        )
//    }
//
//    return OrderResponse(
//        id = this.id,
//        products = productDetails,
//        totalPrice = productDetails.sumOf { it.pricePerUnit * it.quantity },
//        nickname = this.user.nickname, // Assuming there is a nickname in the User
//        name = this.user.name,
//        createdAt = this.createdAt
//    )
//}