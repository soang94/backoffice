package org.example.backoffice.common.exception

data class PasswordMismatchException(val password1: String, val password2: String): RuntimeException(
    "바꾸려는 비밀번호와 재확인 비밀번호가 일치하지 않습니다."
)
