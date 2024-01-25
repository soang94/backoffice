package org.example.backoffice.common.exception

data class InvalidRoleException(val role: String): RuntimeException(
    "invalid ROLE = $role"
)
