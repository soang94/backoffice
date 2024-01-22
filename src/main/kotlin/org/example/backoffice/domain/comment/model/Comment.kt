package org.example.backoffice.domain.comment.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment (
    @Column(name = "name") var name: String,
    @Column(name = "content") var content: String,
    @Column(name = "created_at") var createdAt: LocalDateTime
    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}