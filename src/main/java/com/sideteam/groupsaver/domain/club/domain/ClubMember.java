package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "club_id")
    private long clubId;
    @Column(name = "member_id")
    private long memberId;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberStatus status = ClubMemberStatus.ACTIVITY;
    private ClubMemberRole role;

    private ClubMember(long clubId, long memberId, ClubMemberRole role) {
        this.clubId = clubId;
        this.memberId = memberId;
        this.role = role;
    }

    public static ClubMember of(long clubId, long memberId, ClubMemberRole role) {
        return new ClubMember(clubId, memberId, role);
    }

    public void updateMemberStatus(ClubMemberStatus status) {
        this.status = status;
    }
}