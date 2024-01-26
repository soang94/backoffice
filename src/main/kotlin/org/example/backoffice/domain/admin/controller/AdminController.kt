package org.example.backoffice.domain.admin.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.common.exception.dto.BaseResponse
import org.example.backoffice.domain.admin.dto.AdminDTO
import org.example.backoffice.domain.user.dto.UserResponse
import org.example.backoffice.domain.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/admins")
@RestController
class AdminController(
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

    @Operation(summary = "user 등급 변경")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users")
    fun userChangeAdmin(userId: Long, request: AdminDTO): BaseResponse<AdminDTO> {
        val resultMsg: String = userService.userChangeAdmin(userId,request)
        return BaseResponse(message = resultMsg)
    }


    @Operation(summary = "user 삭제")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users")
    fun deleteUser(userId: Long): ResponseEntity<Unit> {
        userService.deleteUser(userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}