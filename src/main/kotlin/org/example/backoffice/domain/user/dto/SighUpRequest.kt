package org.example.backoffice.domain.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class SighUpRequest(
    @field: NotBlank
    @field: Pattern(
        regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$",
        message = "이메일의 형식에 맞게 입력해주세요"
    )
    @JsonProperty("email")
    private val _email: String?,

    @field: NotBlank
    @field: Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,15}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~15자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field: NotBlank
    @field: Pattern(
        regexp = "^([a-z])(?=.*[0-9])[a-z0-9]{4,10}\$",
        message = "소문자 영문, 숫자를 포함한 4~10자리로 입력해주세요"
    )
    @JsonProperty("nickname")
    private val _nickname: String?,

    val name: String,

    @field: NotBlank
    @field: Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthdate")
    private val _birthdate: String?,

    val info: String,
    val role: String,
){
    val email: String
        get() = _email!!
    val password: String
        get() = _password!!
    val nickname: String
        get() = _nickname!!

    val birthdate: LocalDate
        get() = _birthdate!!.toLocalDate()

    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}
