package org.example.backoffice.domain.member.service

import org.example.backoffice.common.exception.ModelNotFoundException
import org.example.backoffice.domain.member.dto.LoginRequest
import org.example.backoffice.domain.member.dto.LoginResponse
import org.example.backoffice.domain.member.dto.MemberResponse
import org.example.backoffice.domain.member.dto.SighUpRequest
import org.example.backoffice.domain.member.model.Member
import org.example.backoffice.domain.member.model.checkedEmailOrNicknameExists
import org.example.backoffice.domain.member.model.toResponse
import org.example.backoffice.domain.member.repository.MemberRepository
import org.example.backoffice.domain.member.repository.MemberRole
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    private val memberRepository: MemberRepository
): MemberService {
    override fun memberList(): List<MemberResponse> {
        return memberRepository.findAll().map { it.toResponse() }
    }

    override fun member(memberId: Long): MemberResponse {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId)

        return member.toResponse()
    }

    override fun login(request: LoginRequest): LoginResponse {
        val member = memberRepository.findByEmail(request.email) ?: throw ModelNotFoundException("Member", null)

        TODO("password 일치하는지 확인하고 invalidException 던져줘야하고, loginResponse 로 토큰 받아와야함")
    }

    override fun signUp(request: SighUpRequest): MemberResponse {
        checkedEmailOrNicknameExists(request.email, request.nickname, memberRepository)

        return memberRepository.save(
            Member (
                email = request.email,
                password = request.password,
                name = request.name,
                nickname = request.nickname,
                birthdate = request.birthdate,
                createdAt = request.createdAt,
                tmi = request.tmi,
                role = when (request.role) {
                    "ADMIN" -> MemberRole.ADMIN
                    "MEMBER" -> MemberRole.MEMBER
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }
}