package org.example.backoffice.domain.user.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="app_user")
class User (
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
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

