package org.example.backoffice.domain.user.model

import jakarta.persistence.*
import org.example.backoffice.common.model.BaseTime
import org.example.backoffice.domain.user.dto.UpdateProfileRequest
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.repository.UserRepository
import org.example.backoffice.domain.user.repository.UserRole

@Entity
@Table(name="app_user")
class User (
    @Column(name = "email" )
    var email: String,

    @Column(name = "password" )
    var password: String,

    @Column(name = "name" )
    var name: String,

    @Column(name = "birthDate" )
    var birthDate: String,

    @Column(name = "nickname" )
    var nickname: String,

    @Column(name = "info" )
    var info: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole
):BaseTime(){
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
        birthDate = birthDate,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
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


