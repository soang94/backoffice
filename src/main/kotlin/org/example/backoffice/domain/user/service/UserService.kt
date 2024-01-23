package org.example.backoffice.domain.user.service

import org.example.backoffice.domain.user.dto.LoginRequest
import org.example.backoffice.domain.user.dto.LoginResponse
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.dto.SighUpRequest

interface UserService {
    fun userList(): List<UserResponse>

    fun user(memberId: Long): UserResponse

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SighUpRequest): UserResponse
}