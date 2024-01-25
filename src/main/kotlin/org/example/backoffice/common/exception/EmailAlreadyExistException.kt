package org.example.backoffice.common.exception

data class EmailAlreadyExistException(val email: String): RuntimeException(
    "이미 존재하는 이메일 입니다."
)
