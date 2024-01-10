package com.sideteam.groupsaver.domain.chat.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String image;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;
    @OneToMany(mappedBy = "clubChatRoom", fetch = FetchType.LAZY)
    private List<ChatMember> chatMemberList = new ArrayList<>();

    private ClubChatRoom(String name, String image, Club club) {
        this.name = name;
        this.image = image;
        this.club = club;
    }

    public static ClubChatRoom of(String name, String image, Club club) {
        return new ClubChatRoom(name, image, club);
    }

    public void addChatMember(ChatMember member) {
        this.getChatMemberList().add(member);
    }
}