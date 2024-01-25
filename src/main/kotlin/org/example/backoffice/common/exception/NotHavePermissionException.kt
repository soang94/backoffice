package org.example.backoffice.common.exception

data class NotHavePermissionException(val aId: Long,val bId: Long): RuntimeException(
    "유저의 ID가 $aId 인 사용자는 오더 ID = $bId 를 삭제 할 권한이 없습니다."
)