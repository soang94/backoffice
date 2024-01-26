package org.example.backoffice.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ChangePasswordRequest(
    val password: String,

    @field: NotBlank
    @field: Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,15}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~15자리로 입력해주세요"
    )
    @JsonProperty("changePassword")
    private val _changePassword: String,

    val validatePassword: String

){
    val changePassword: String
        get() = _changePassword

}
