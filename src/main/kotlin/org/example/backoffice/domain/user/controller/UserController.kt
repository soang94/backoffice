package org.example.backoffice.domain.user.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.example.backoffice.common.security.jwt.UserPrincipal
import org.example.backoffice.domain.user.dto.*
import org.example.backoffice.domain.user.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
) {

    @Operation(summary = "user 목록 전체 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    fun userList(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.userList())
    }

    @Operation(summary = "user 단건 조회, user profile")
    @PreAuthorize("hasRole('ADMIN') or #userPrincipal.id == #userId")
    @GetMapping("/users/{userId}")
    fun user(
        @PathVariable userId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.user(userId))
    }

    @Operation(summary = "user profile 수정")
    @PreAuthorize("#userPrincipal.id == #userId")
    @PutMapping("/users/{userId}")
    fun updateProfile(
        @PathVariable userId: Long,
        @RequestBody updateProfileRequest: UpdateProfileRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateProfile(userId, updateProfileRequest))
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<Any> {
        val userDetails = userService.login(loginRequest)
        val headers = HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, userDetails.accessToken)
        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headers)
            .body("로그인에 성공했습니다! accessToken = ${userDetails.accessToken}")
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun sighUp(
        @Valid @RequestBody signUpRequest: SighUpRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }

    @Operation(summary = "비밀번호 재설정")
    @PreAuthorize("#userPrincipal.id == #userId")
    @PatchMapping("/users/{userId}")
    fun changePassword(
        @PathVariable userId: Long,
        @RequestBody changePasswordRequest: ChangePasswordRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.changePassword(userId, changePasswordRequest))
    }
}