package org.example.backoffice.domain.member.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity
@Table(name="member")
class Member (
    @Column(name = "email" ) var email: String,
    @Column(name = "password" ) var password: String,
    @Column(name = "name" ) var name: String,
    @Column(name = "birthdate" ) var birthdate: String,
    @Column(name = "nickname" ) var nickname: String,
    @Column(name = "tmi" ) var tmi: String,
    @Column(name = "created_at" ) var createdAt: LocalDateTime,
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
