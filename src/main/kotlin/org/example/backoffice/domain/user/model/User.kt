package org.example.backoffice.domain.user.model

import jakarta.persistence.*
import org.example.backoffice.common.exception.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.user.dto.UpdateProfileRequest
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.repository.UserRepository
import org.example.backoffice.domain.user.repository.UserRole
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "birthdate")
    var birthdate: String,

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "info")
    var info: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole


) : BaseTime() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun toUpdate(request: UpdateProfileRequest, passwordEncoder: PasswordEncoder) {
        name = request.name
        info = request.info
//        password = passwordEncoder.encode(request.password)
    }
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id!!,
        email = email,
        name = name,
        nickname = nickname,
        info = info,
        birthdate = birthdate,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        role = role.name,
    )
}

fun checkedEmailOrNicknameExists(email: String, nickname: String, userRepository: UserRepository) {
    if (userRepository.existsByEmail(email)) {
        throw EmailAlreadyExistException(email)
    }

    if (userRepository.existsByNickname(nickname)) {
        throw NicknameAlreadyExistException(nickname)
    }
}

fun checkedLoginPassword(password: String, inputPassword: String, passwordEncoder: PasswordEncoder) {
    if(!passwordEncoder.matches(inputPassword, password)) {
        throw InvalidPasswordException(inputPassword)
    }
}

fun checkedPassword(password: String, inputPassword: String, passwordEncoder: PasswordEncoder) {
    if(!passwordEncoder.matches(inputPassword, password)) throw WrongPasswordException(inputPassword)
}

fun checkedChangePassword(changePassword: String, validatePassword: String) {
    if(changePassword != validatePassword) throw PasswordMismatchException(changePassword, validatePassword)
}



