package org.example.backoffice.domain.order.exception

data class ModelNotfoundException(val modelName: String, val id: Long?) :
    RuntimeException("Moder $modelName Not found with given id: $id")
