package com.sideteam.groupsaver.domain.category.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "club_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(value = EnumType.STRING)
    private DevelopMajor major;
    @OneToMany(mappedBy = "clubCategory")
    private List<Club> clubList = new ArrayList<>();

    public ClubCategory(DevelopMajor major) {
        this.major = major;
    }

    public static ClubCategory of(Club club, DevelopMajor categoryType) {
        ClubCategory category = new ClubCategory(categoryType);
        category.getClubList().add(club);
        return category;
    }

    public void addAClub(Club club) {
        this.getClubList().add(club);
    }
}