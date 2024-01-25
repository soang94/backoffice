package org.example.backoffice.domain.user.service

import jakarta.validation.constraints.Max
import org.example.backoffice.common.exception.InvalidPasswordException
import org.example.backoffice.common.exception.InvalidRoleException
import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.common.exception.WrongPasswordConfirmException
import org.example.backoffice.common.security.jwt.JwtPlugin
import org.example.backoffice.domain.user.dto.*
import org.example.backoffice.domain.user.model.User
import org.example.backoffice.domain.user.model.checkedEmailOrNicknameExists
import org.example.backoffice.domain.user.model.toResponse
import org.example.backoffice.domain.user.repository.UserRepository
import org.example.backoffice.domain.user.repository.UserRole
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {
    override fun userList(): List<UserResponse> {
        return userRepository.findAll().map { it.toResponse() }
    }

    override fun user(userId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        return user.toResponse()
    }

    @Transactional
    override fun updateProfile(userId: Long, request: UpdateProfileRequest): UserResponse {
        val profile = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        if(request.password != request.passwordConfirm){
            throw WrongPasswordConfirmException("입력하신 비밀번호와 비밀번호 확인이 맞지 않습니다. 다시 입력해주세요.")
        }else {
                profile.toUpdate(request)
                userRepository.save(profile)
                return profile.toResponse()

        }
    }


    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

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
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                name = request.name,
                nickname = request.nickname,
                birthdate = request.birthdate.toString(),
                info = request.info,
                role = when (request.role) {
                    "ADMIN" -> UserRole.ADMIN
                    "MEMBER" -> UserRole.MEMBER
                    else -> throw InvalidRoleException(request.role)
                }
            )
        ).toResponse()
    }
}