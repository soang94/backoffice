package org.example.backoffice.common.exception

data class NotHavePermissionException(val aId: Long, val bId: Long) : RuntimeException(
    "$aId 는 $bId 에 권한이 없습니다"
)
