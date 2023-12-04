package com.sideteam.groupsaver.domain.club.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "club_id")
    private long clubId;
    @Column(name = "member_id")
    private long memberId;
    private ClubMemberStatus status = ClubMemberStatus.STANDBY;
    private ClubMemberRole role = ClubMemberRole.MEMBER;

    private ClubMember(long clubId, long memberId) {
        this.clubId = clubId;
        this.memberId = memberId;
    }

    public static ClubMember of(long clubId, long memberId) {
        return new ClubMember(clubId, memberId);
    }

    public void updateMemberStatus(ClubMemberStatus status) {
        this.status = status;
    }

    public void updateMemberRole(ClubMemberRole role) {
        this.role = role;
    }
}