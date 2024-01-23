package org.example.backoffice.domain.product.model

import jakarta.persistence.*
import org.example.backoffice.domain.product.dto.CategoryResponse
import org.example.backoffice.domain.product.dto.ProductResponse
import java.time.LocalDateTime

@Entity
@Table(name = "category")
class Category(
    @Column(name = "name") var name: String,
    @Column(name = "info") var info: String,
    @Column(name = "created_at") var createdAt: LocalDateTime,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = id,
        name = name,
        info = info,
        createdAt = this.createdAt
    )
}