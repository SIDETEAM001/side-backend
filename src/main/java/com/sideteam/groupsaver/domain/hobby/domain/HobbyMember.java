package com.sideteam.groupsaver.domain.hobby.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOBBY_ID")
    private Hobby hobby;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberStatus status = ClubMemberStatus.ACTIVITY;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberRole role;

    private HobbyMember(Hobby hobby, Member member, ClubMemberRole role) {
        this.hobby = hobby;
        this.member = member;
        this.role = role;
    }

    public static HobbyMember of(Hobby hobby, Member member, ClubMemberRole role) {
        return new HobbyMember(hobby, member, role);
    }
}
