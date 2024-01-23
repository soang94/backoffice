package org.example.backoffice.domain.product.dto

import java.time.LocalDateTime

data class CategoryCreateRequest (
    var name : String,
    var info : String
)
