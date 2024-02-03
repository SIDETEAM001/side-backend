package com.sideteam.groupsaver.domain.notification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PushAlarmMessage {
    NEW_CLUB("추천할만한 새로운 모임이 있습니다!"),
    CLUB_SCHEDULE("모임에 새로운 일정이 등록되었습니다"),
    CLUB_NEW_MEMBER("모임에 새로운 멤버가 들어왔습니다"),
    ;

    private final String message;
}
