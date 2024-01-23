package org.example.backoffice.domain.user.model

import jakarta.persistence.*
import org.example.backoffice.domain.user.dto.UpdateProfileRequest
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.repository.UserRepository
import org.example.backoffice.domain.user.repository.UserRole
import java.time.LocalDateTime

@Entity
@Table(name="app_user")
class User (
    @Column(name = "email" )
    var email: String,

    @Column(name = "password" )
    var password: String,

    @Column(name = "name" )
    var name: String,

    @Column(name = "birthdate" )
    var birthdate: String,

    @Column(name = "nickname" )
    var nickname: String,

    @Column(name = "info" )
    var info: String,

    @Column(name = "created_at" )
    var createdAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun toUpdate(request: UpdateProfileRequest) {
        name = request.name
        info = request.info
        password = request.password
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
        createdAt = createdAt,
        role = role.name,
    )
}

fun checkedEmailOrNicknameExists(email: String, nickname: String, userRepository: UserRepository) {
    if (userRepository.existsByEmail(email)) {
        throw IllegalArgumentException("이미 사용 중인 이메일입니다.")
    }

    if (userRepository.existsByNickname(nickname)) {
        throw IllegalArgumentException("이미 사용 중인 닉네임입니다.")
    }
}


