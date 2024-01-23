package org.example.backoffice.domain.user.dto


data class SighUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val birthdate: String,
    val info: String,
    val role: String,
)
