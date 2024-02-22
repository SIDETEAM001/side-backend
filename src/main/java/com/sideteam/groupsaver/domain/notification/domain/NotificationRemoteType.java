package com.sideteam.groupsaver.domain.notification.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationRemoteType {

    NEW_CLUB(NotificationType.PUBLIC_NOTI, "모임", "추천할만한 새로운 모임이 있습니다!"),
    AD(NotificationType.PUBLIC_NOTI,"광고", "내일 시작하는 건 이제 그만!\n지금 바로 자기계발 모임에 참여해보세요! "),

    SCHEDULE(NotificationType.PRIVATE_NOTI, "일정", "모임에 새로운 일정이 등록되었습니다"),
    NEW_MEMBER(NotificationType.PRIVATE_NOTI,"모임 신규 회원", "모임에 새로운 멤버가 들어왔습니다"),
    ;

    private final NotificationType type;
    private final String remoteType;
    private final String message;

    NotificationRemoteType(NotificationType type, String remoteType) {
        this.type = type;
        this.remoteType = remoteType;
        this.message = null;
    }
}
