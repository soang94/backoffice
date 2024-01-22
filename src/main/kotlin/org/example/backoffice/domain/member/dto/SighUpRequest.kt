package org.example.backoffice.domain.member.dto

data class SighUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val birth: String,
    val role: String,
)
