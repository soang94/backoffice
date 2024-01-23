package org.example.backoffice.domain.order.model

import jakarta.persistence.*

@Entity
@Table(name="orders")

class Order (){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
