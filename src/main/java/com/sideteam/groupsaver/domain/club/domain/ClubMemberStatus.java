package com.sideteam.groupsaver.domain.club.domain;

import lombok.Getter;

@Getter
public enum ClubMemberStatus {
    ACTIVITY("활동중"),
    WITHDRAWAL("탈퇴")
    ;

    private final String ClubMemberStatus;

    ClubMemberStatus(String clubMemberStatus) {
        ClubMemberStatus = clubMemberStatus;
    }
}
