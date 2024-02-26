package com.sideteam.groupsaver.domain.notification.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NotificationType {
    PUBLIC("전체알림"),
    PRIVATE("내알림"),
    ;
    private final String type;
}
