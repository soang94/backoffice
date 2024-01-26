package org.example.backoffice.common.exception

data class WrongPasswordException(val password: String):  RuntimeException(
    "기존 비밀번호가 틀렸습니다."
)
