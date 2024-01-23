package org.example.backoffice.domain.user.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.domain.user.dto.LoginRequest
import org.example.backoffice.domain.user.dto.LoginResponse
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.dto.SighUpRequest
import org.example.backoffice.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @Operation(summary = "user 목록 전체 조회")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    fun userList(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.userList())
    }

    @Operation(summary = "member 단건 조회")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER')")
    @GetMapping("/users/{userId}")
    fun user(
        @PathVariable userId: Long
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.user(userId))
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.login(loginRequest))
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun sighUp(
        @RequestBody signUpRequest: SighUpRequest
    ): ResponseEntity<UserResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userService.signUp(signUpRequest))
    }
}