package org.example.backoffice.domain.product.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "table")
class Product (
    @Column(name = "name") var name: String,
    @Column(name = "price") var price: Long,
    @Column(name = "title") var title: String,
    @Column(name = "info") var info: String,
    @Column(name = "category") var category: String,
    @Column(name = "created_at") var createdAt: LocalDateTime
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
