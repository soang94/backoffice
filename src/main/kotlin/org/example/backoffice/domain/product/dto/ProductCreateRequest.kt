package org.example.backoffice.domain.product.dto

import java.time.LocalDateTime

class ProductCreateRequest (
  var userId : Long?,
  var name: String,
  var price: Long,
  var title: String,
  var info: String,
  var categoryId: Long,
  var createdAt : LocalDateTime,
  var updatedAt : LocalDateTime?
)