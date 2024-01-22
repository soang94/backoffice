package org.example.backoffice.domain.comment.repository

import org.example.backoffice.domain.comment.model.Comments
import org.springframework.data.jpa.repository.JpaRepository

interface CommentsRepository : JpaRepository<Comments, Long> {
}