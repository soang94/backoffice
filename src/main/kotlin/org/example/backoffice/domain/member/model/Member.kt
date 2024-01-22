package org.example.backoffice.domain.member.model

import jakarta.persistence.*
import org.example.backoffice.domain.member.dto.MemberResponse
import org.example.backoffice.domain.member.repository.MemberRole
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity
@Table(name="member")
class Member (
    @Column(name = "email" )
    var email: String,

    @Column(name = "password" )
    var password: String,

    @Column(name = "name" )
    var name: String,

    @Column(name = "birthdate" )
    var birthdate: String,

    @Column(name = "nickname" )
    var nickname: String,

    @Column(name = "tmi" )
    var tmi: String,

    @Column(name = "created_at" )
    var createdAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: MemberRole
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = id!!,
        email = email,
        name = name,
        nickname = nickname,
        tmi = tmi,
        birthdate = birthdate,
        createdAt = createdAt,
        role = role.name,
    )
}

