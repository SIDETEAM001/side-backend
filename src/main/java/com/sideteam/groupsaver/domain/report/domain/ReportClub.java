package com.sideteam.groupsaver.domain.report.domain;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportClub extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLUB_ID")
    private Club club;

    @Enumerated(value = EnumType.STRING)
    private ReportClubCategory category;

    private String etcReason;

    @Builder
    protected ReportClub(Club club, ReportClubCategory category, String etcReason) {
        this.club = club;
        this.category = category;
        this.etcReason = etcReason;
    }

    public static ReportClub of(Club club, ReportClubCategory category, String etcReason) {
        return ReportClub.builder()
                .club(club)
                .category(category)
                .etcReason(etcReason)
                .build();
    }
}
