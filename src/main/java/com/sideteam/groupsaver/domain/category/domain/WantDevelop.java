package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "want_develop")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WantDevelop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private DevelopMajor major;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
