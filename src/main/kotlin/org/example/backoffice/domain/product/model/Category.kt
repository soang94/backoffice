package org.example.backoffice.domain.product.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.product.dto.CategoryResponse

@Entity
@Table(name = "category")
class Category(
    @Column(name = "name") var name: String,
    @Column(name = "info") var info: String,
) : BaseTime() {
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