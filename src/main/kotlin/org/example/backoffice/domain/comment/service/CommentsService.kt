package org.example.backoffice.domain.comment.service

import org.example.backoffice.domain.comment.dto.CommentsRequest
import org.example.backoffice.domain.comment.dto.CommentsResponse
import org.example.backoffice.domain.comment.dto.DeleteCommentRequest

interface CommentsService {

    fun getComments(productId: Long, commentId: Long): CommentsResponse

    fun createComments(productId: Long, request: CommentsRequest): CommentsResponse

    fun updateComments(productId: Long, commentId: Long, request: CommentsRequest): CommentsResponse

    fun deleteComments(productId: Long, commentId: Long, request: DeleteCommentRequest)
}