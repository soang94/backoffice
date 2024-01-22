package org.example.backoffice.domain.member.dto

data class LoginRequest (
    val email: String,
    val password: String
)