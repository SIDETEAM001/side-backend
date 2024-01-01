package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "club_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private DevelopMajor major;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    private ClubCategory(Club club, DevelopMajor major) {
        this.club = club;
        this.major = major;
    }

    public static ClubCategory of(Club club, DevelopMajor categoryType) {
        return new ClubCategory(club, categoryType);
    }
}
