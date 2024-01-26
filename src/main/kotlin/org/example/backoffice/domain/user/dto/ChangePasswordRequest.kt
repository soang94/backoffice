package org.example.backoffice.domain.user.dto

data class ChangePasswordRequest(
    val password: String,
    val changePassword: String,
    val validatePassword: String,
)

