package com.sideteam.groupsaver.domain.notification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NotificationType {
    REC_CLUB("추천 모임"),
    CLUB_SCHEDULE("모임 일정"),
    NEW_MEMBER("새로운 모임 회원"),
    PUBLIC("공지"),
    ;
    private final String type;
}
