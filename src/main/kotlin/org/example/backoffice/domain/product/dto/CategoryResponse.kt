package org.example.backoffice.domain.product.dto

import java.time.LocalDateTime

data class CategoryResponse (
    val id : Long?,
    val name : String,
    val info : String,
    var createdAt : LocalDateTime,
)