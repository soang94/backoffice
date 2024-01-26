package org.example.backoffice.domain.user.service

import org.example.backoffice.common.exception.*
import org.example.backoffice.common.security.jwt.JwtPlugin
import org.example.backoffice.domain.admin.dto.AdminDTO
import org.example.backoffice.domain.user.dto.*
import org.example.backoffice.domain.user.model.*
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
        profile.toUpdate(request, passwordEncoder)
        userRepository.save(profile)
        return profile.toResponse()
    }

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        checkedLoginPassword(user.password, request.password, passwordEncoder)

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
                },
            )
        ).toResponse()
    }

    @Transactional
    override fun changePassword(userId: Long, request: ChangePasswordRequest) {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)


        checkedPassword(user.password, request.password, passwordEncoder)
        checkedChangePassword(request.changePassword, request.validatePassword)

        user.password = passwordEncoder.encode(request.changePassword)
        userRepository.save(user)

    }

    @Transactional
    override fun userChangeAdmin(userId: Long, request: AdminDTO): String {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)

        if(user.role == UserRole.MEMBER || user.role == UserRole.ADMIN)
        user.role = request.role

        userRepository.save(user)


        return "등급이 변경되었습니다."

    }

    @Transactional
    override fun deleteUser(userId: Long): String {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("해당 ID를 가진 사용자를 찾을 수 없습니다.")
        }

        userRepository.delete(user)
        return "삭제되었습니다."
    }

    @Transactional
    override fun deleteMyUser(userId: Long): String {
        val user = userRepository.findById(userId).orElseThrow {
            NoSuchElementException("해당 ID를 가진 사용자를 찾을 수 없습니다.")
        }

        userRepository.delete(user)
        return "탈퇴처리 되었습니다."
    }
}