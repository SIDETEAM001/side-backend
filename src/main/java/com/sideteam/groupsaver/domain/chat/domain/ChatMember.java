package com.sideteam.groupsaver.domain.chat.domain;

import com.sideteam.groupsaver.domain.club.domain.ClubMemberRole;
import com.sideteam.groupsaver.domain.club.domain.ClubMemberStatus;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubChatRoom_id")
    private ClubChatRoom clubChatRoom;
    private boolean isAlarm = true;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberStatus status = ClubMemberStatus.ACTIVITY;
    @Enumerated(value = EnumType.STRING)
    private ClubMemberRole role;

    private ChatMember(Member member, ClubChatRoom clubChatRoom, ClubMemberRole role) {
        this.member = member;
        this.clubChatRoom = clubChatRoom;
        this.role = role;
    }

    public static ChatMember of(Member member, ClubChatRoom clubChatRoom, ClubMemberRole role) {
        return new ChatMember(member, clubChatRoom, role);
    }
}