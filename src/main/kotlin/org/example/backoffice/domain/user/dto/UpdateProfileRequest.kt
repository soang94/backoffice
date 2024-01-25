package org.example.backoffice.domain.user.dto


data class UpdateProfileRequest(
    val name: String,
    val info: String,
    val password: String,
    val passwordConfirm: String
)
