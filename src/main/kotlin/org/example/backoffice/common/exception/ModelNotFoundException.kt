package org.example.backoffice.common.exception

data class ModelNotFoundException(val modelName: String, val id: Any) : RuntimeException(
    "Model $modelName not found with given id: $id"
)