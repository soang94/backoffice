package org.example.backoffice.domain.user.service

import org.example.backoffice.domain.admin.dto.AdminDTO
import org.example.backoffice.domain.user.dto.*

interface UserService {
    fun userList(): List<UserResponse>

    fun user(userId: Long): UserResponse

    fun updateProfile(userId: Long, request: UpdateProfileRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SighUpRequest): UserResponse

    fun changePassword(userId: Long, request: ChangePasswordRequest)

    fun userChangeAdmin(userId: Long, request: AdminDTO): String

    fun deleteUser(userId: Long): String

    fun deleteMyUser(userId: Long): String
}