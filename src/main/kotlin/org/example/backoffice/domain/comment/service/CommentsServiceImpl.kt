package org.example.backoffice.domain.comment.service

import org.example.backoffice.domain.comment.dto.CommentsRequest
import org.example.backoffice.domain.comment.dto.CommentsResponse
import org.example.backoffice.domain.comment.dto.DeleteCommentRequest
import org.example.backoffice.domain.comment.model.Comments
import org.example.backoffice.domain.comment.model.toResponse
import org.example.backoffice.domain.comment.repository.CommentsRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentsServiceImpl(
    private val commentsRepository: CommentsRepository
) : CommentsService {

    //댓글 단건 조회
    override fun getComments(productId: Long, commentId: Long): CommentsResponse {
        val comment =
            commentsRepository.findByIdOrNull(commentId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시시도해주세요")
        return comment.toResponse()
    }


    //댓글 작성
    override fun createComments(productId: Long, request: CommentsRequest): CommentsResponse {
        val createComments = commentsRepository.save(
            Comments(
                name = request.name,
                content = request.content,
                password = request.password,
                createdAt = request.createdAt,
                updatedAt = request.updatedAt
            )
        )
        return createComments.toResponse()
    }

    //댓글 수정
    @Transactional
    override fun updateComments(productId: Long, commentId: Long, request: CommentsRequest): CommentsResponse {
        val comment =
            commentsRepository.findByIdOrNull(commentId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시 시도해주세요")


        if (comment.password != request.password)
            throw IllegalStateException("맞지 않는 비밀번호입니다. 다시 시도해주세요")
        else {
            comment.name = request.name
            comment.content = request.content
            commentsRepository.save(comment)

            return comment.toResponse()
        }
    }

    //댓글 삭제
    @Transactional
    override fun deleteComments(productId: Long, commentId: Long, request: DeleteCommentRequest) {
        val comment =
            commentsRepository.findByIdOrNull(commentId) ?: throw IllegalStateException("알맞은 데이터가 없습니다.다시시도해주세요")

        if (comment.password != request.password)
            throw IllegalStateException("맞지 않는 비밀번호입니다. 다시 시도해주세요")
        else {
            commentsRepository.delete(comment)
            commentsRepository.save(comment)
        }

    }


}