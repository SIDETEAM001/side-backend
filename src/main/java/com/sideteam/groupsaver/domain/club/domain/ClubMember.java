package com.sideteam.groupsaver.domain.club.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberStatus status = ClubMemberStatus.ACTIVITY;
    private ClubMemberRole role;

    public ClubMember(Club club, Member member, ClubMemberRole role) {
        this.club = club;
        this.member = member;
        this.role = role;
    }

    public static ClubMember of(Club club, Member member, ClubMemberRole role) {
        return new ClubMember(club, member, role);
    }

    public void updateMemberStatus(ClubMemberStatus status) {
        this.status = status;
    }
}