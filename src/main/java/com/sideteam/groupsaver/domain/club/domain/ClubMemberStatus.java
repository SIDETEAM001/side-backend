package com.sideteam.groupsaver.domain.club.domain;

import lombok.Getter;

@Getter
public enum ClubMemberStatus {
    ACTIVITY("활동중"),
    WITHDRAWAL("탈퇴"),
    STANDBY("신청대기");

    private final String ClubMemberStatus;

    ClubMemberStatus(String clubMemberStatus) {
        ClubMemberStatus = clubMemberStatus;
    }
}
