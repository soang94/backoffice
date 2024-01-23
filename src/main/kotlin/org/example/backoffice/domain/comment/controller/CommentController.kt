package org.example.backoffice.domain.comment.controller

import org.example.backoffice.domain.comment.dto.CommentsRequest
import org.example.backoffice.domain.comment.dto.CommentsResponse
import org.example.backoffice.domain.comment.service.CommentsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/products/{productId}/comments/{commentId}")
@RestController
class CommentController(
    private val commentsService: CommentsService
) {

    //댓글 단건 조회
    @GetMapping
    fun getComments(
        @PathVariable productId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<CommentsResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.getComments(productId, commentId))
    }

    //댓글 작성
    @PostMapping
    fun createComments(
        @PathVariable productId: Long,
        @RequestBody request: CommentsRequest
    ): ResponseEntity<CommentsResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentsService.createComments(productId, request))
    }


    //댓글 수정
    @PatchMapping
    fun updateComments(
        @PathVariable productId: Long,
        @PathVariable commentId: Long,
        @RequestBody request: CommentsRequest
    ): ResponseEntity<CommentsResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(commentsService.updateComments(productId, commentId, request))
    }
    //댓글 삭제
    @DeleteMapping
    fun deleteComments(
        @PathVariable productId: Long,
        @PathVariable commentId: Long,
    ): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}




