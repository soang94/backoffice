package org.example.backoffice.domain.member.service

import org.example.backoffice.domain.member.dto.LoginRequest
import org.example.backoffice.domain.member.dto.LoginResponse
import org.example.backoffice.domain.member.dto.MemberResponse
import org.example.backoffice.domain.member.dto.SighUpRequest
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl: MemberService {
    override fun memberList(): List<MemberResponse> {
        TODO("Not yet implemented")
    }

    override fun member(memberId: Long): MemberResponse {
        TODO("Not yet implemented")
    }

    override fun login(request: LoginRequest): LoginResponse {
        TODO("Not yet implemented")
    }

    override fun signUp(request: SighUpRequest): MemberResponse {
        TODO("Not yet implemented")
    }
}