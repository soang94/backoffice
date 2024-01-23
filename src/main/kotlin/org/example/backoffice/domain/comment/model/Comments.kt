package org.example.backoffice.domain.comment.model

import jakarta.persistence.*
import org.example.backoffice.domain.comment.dto.CommentsResponse
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class Comments(
    @Column(name = "name") var name: String,
    @Column(name = "content") var content: String,
    @Column(name = "password") var password: String,
    @Column(name = "created_at") var createdAt: LocalDateTime,
    @Column(name = "updated_at") var updatedAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}

fun Comments.toResponse(): CommentsResponse {
    return CommentsResponse(
        id = id!!,
        name = name,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}