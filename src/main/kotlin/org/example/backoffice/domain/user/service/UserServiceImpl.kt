package org.example.backoffice.domain.user.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.common.security.jwt.JwtPlugin
import org.example.backoffice.domain.user.dto.LoginRequest
import org.example.backoffice.domain.user.dto.LoginResponse
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.dto.SighUpRequest
import org.example.backoffice.domain.user.model.User
import org.example.backoffice.domain.user.model.checkedEmailOrNicknameExists
import org.example.backoffice.domain.user.model.toResponse
import org.example.backoffice.domain.user.repository.UserRepository
import org.example.backoffice.domain.user.repository.UserRole
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
): UserService {
    override fun userList(): List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    override fun user(memberId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return user.toResponse()
    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Member", null)

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            )
        )
    }

    override fun signUp(request: SighUpRequest): UserResponse {
        checkedEmailOrNicknameExists(request.email, request.nickname, userRepository)

        return userRepository.save(
            User (
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                nickname = request.nickname,
                birthdate = request.birthdate,
                createdAt = request.createdAt,
                info = request.info,
                role = when (request.role) {
                    "ADMIN" -> UserRole.ADMIN
                    "MEMBER" -> UserRole.MEMBER
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }
}