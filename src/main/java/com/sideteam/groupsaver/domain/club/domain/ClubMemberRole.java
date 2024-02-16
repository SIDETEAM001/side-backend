package com.sideteam.groupsaver.domain.club.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ClubMemberRole {
    LEADER("모임리더"),
    MEMBER("팀원");

    private final String clubMemberRole;

    ClubMemberRole(String clubMemberRole) {
        this.clubMemberRole = clubMemberRole;
    }


    @JsonValue
    public String getJsonValue() {
        return clubMemberRole;
    }

}
