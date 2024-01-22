package org.example.backoffice.domain.member.controller

import io.swagger.v3.oas.annotations.Operation
import org.example.backoffice.domain.member.dto.LoginRequest
import org.example.backoffice.domain.member.dto.MemberResponse
import org.example.backoffice.domain.member.dto.SighUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController {

    @Operation(summary = "member 목록 전체 조회")
    @GetMapping
    fun memberList(): ResponseEntity<List<MemberResponse>> {
        TODO()
    }

    @Operation(summary = "member 단건 조회")
    @GetMapping("/members/{memberId}")
    fun member(
        @PathVariable memberId: Long
    ): ResponseEntity<MemberResponse> {
        TODO()
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<MemberResponse> {
        TODO()
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun sighUp(
        @RequestBody signUpRequest: SighUpRequest
    ): ResponseEntity<MemberResponse> {
        TODO()
    }
}