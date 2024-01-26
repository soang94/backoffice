package org.example.backoffice.domain.user.repository

import org.example.backoffice.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsByNickname(nickname: String): Boolean
}