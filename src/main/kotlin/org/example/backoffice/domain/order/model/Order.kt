package org.example.backoffice.domain.order.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime

@Entity
@Table(name="orders")

class Order () : BaseTime(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
