package com.sideteam.groupsaver.domain.join.domain;

import com.sideteam.groupsaver.domain.category.domain.ClubCategoryMajor;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class WantClubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubCategoryMajor categoryMajor;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    private WantClubCategory(Member member, ClubCategoryMajor categoryMajor) {
        this.member = member;
        this.categoryMajor = categoryMajor;
    }

    public static WantClubCategory of(Member member, ClubCategoryMajor categoryMajor) {
        return new WantClubCategory(member, categoryMajor);
    }

}
