package org.example.backoffice.domain.user.dto

import java.time.LocalDateTime

data class UpdateProfileRequest(
    val name: String,
    val info: String,
    val password: String,
)
