package org.example.backoffice.common.exception

data class InvalidPasswordException(val password: String): RuntimeException(
    "맞지 않는 비밀번호 입니다. 다시 시도해주세요."
)