package org.example.backoffice.domain.admin.dto

import org.example.backoffice.domain.user.repository.UserRole

data class AdminDTO(
    val role: UserRole
)
