package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "want_hobby")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WantHobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private HobbyMajor major;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}