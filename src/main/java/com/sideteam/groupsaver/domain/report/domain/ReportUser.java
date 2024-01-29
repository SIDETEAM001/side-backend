package com.sideteam.groupsaver.domain.report.domain;

import com.sideteam.groupsaver.domain.common.BaseEntity;
import com.sideteam.groupsaver.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private ReportUserCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String etcReason;

    @Builder
    protected ReportUser(ReportUserCategory category, Member member, String etcReason) {
        this.category = category;
        this.member = member;
        this.etcReason = etcReason;
    }

    public static ReportUser of(ReportUserCategory category, Member member, String etcReason) {
        return ReportUser.builder()
                .category(category)
                .member(member)
                .etcReason(etcReason)
                .build();
    }
}
