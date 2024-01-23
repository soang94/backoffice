package org.example.backoffice.domain.user.dto

import java.time.LocalDateTime

data class SighUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val nickname: String,
    val birthdate: String,
    val createdAt: LocalDateTime,
    val info: String,
    val role: String,
)
