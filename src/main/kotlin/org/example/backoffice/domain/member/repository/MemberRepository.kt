package org.example.backoffice.domain.member.repository

import org.example.backoffice.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
}