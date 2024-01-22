package org.example.backoffice.domain.member.dto

import java.time.LocalDateTime

data class MemberResponse (
    val id: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val tmi: String,
    val createAt: LocalDateTime,
    val role: String,
)