package org.example.backoffice.domain.user.service

import org.example.backoffice.domain.user.dto.*

interface UserService {
    fun userList(): List<UserResponse>

    fun user(userId: Long): UserResponse

    fun updateProfile(userId: Long, request: UpdateProfileRequest): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SighUpRequest): UserResponse
}