package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity(name = "club_category")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private DevelopMajor major;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;
}
