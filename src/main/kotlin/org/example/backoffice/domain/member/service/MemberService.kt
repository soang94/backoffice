package org.example.backoffice.domain.member.service

import org.example.backoffice.domain.member.dto.LoginRequest
import org.example.backoffice.domain.member.dto.LoginResponse
import org.example.backoffice.domain.member.dto.MemberResponse
import org.example.backoffice.domain.member.dto.SighUpRequest

interface MemberService {
    fun memberList(): List<MemberResponse>

    fun member(memberId: Long): MemberResponse

    fun login(request: LoginRequest): LoginResponse

    fun signUp(request: SighUpRequest): MemberResponse
}