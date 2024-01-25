package org.example.backoffice.common.exception

data class LikeException(val id: Any) : RuntimeException(
    "이 id로는 좋아요를 할 수 없습니다.: $id"
)