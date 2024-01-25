package org.example.backoffice.domain.product.dto

import jakarta.persistence.Column
import org.example.backoffice.domain.product.model.Category
import org.example.backoffice.domain.review.dto.ReviewResponse
import org.example.backoffice.domain.review.model.Review
import java.time.LocalDateTime
import java.time.ZonedDateTime

class ProductResponse (
    val id : Long?,
    val userId : Long?,
    val name : String,
    val price : Long,
    val title : String,
    val info : String,
    val categoryId : Long,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime?,
    var review: List<ReviewResponse>,
    val countLiked: Int
)
