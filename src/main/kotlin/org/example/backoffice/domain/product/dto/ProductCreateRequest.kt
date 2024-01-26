package org.example.backoffice.domain.product.dto


class ProductCreateRequest(
    var name: String?,
    var price: Long?,
    var title: String?,
    var info: String?,
    var categoryId: Long?,
)