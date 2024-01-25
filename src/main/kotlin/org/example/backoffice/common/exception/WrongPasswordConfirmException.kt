package org.example.backoffice.common.exception

data class WrongPasswordConfirmException(val password: String) : RuntimeException(
    "입력하신 비밀번호와 비밀번호 확인이 맞지 않습니다. 다시 입력해주세요."
)