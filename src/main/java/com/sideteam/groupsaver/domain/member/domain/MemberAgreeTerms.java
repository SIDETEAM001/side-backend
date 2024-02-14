package com.sideteam.groupsaver.domain.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MemberAgreeTerms {

    @Column(nullable = false)
    private boolean ageTerm;

    @Column(nullable = false)
    private boolean serviceTerm;

    @Column(nullable = false)
    private boolean userInfoTerm;

    @Column(nullable = false)
    private boolean locationTerm;

    @Builder
    protected MemberAgreeTerms(boolean ageTerm, boolean serviceTerm, boolean userInfoTerm, boolean locationTerm) {
        this.ageTerm = ageTerm;
        this.serviceTerm = serviceTerm;
        this.userInfoTerm = userInfoTerm;
        this.locationTerm = locationTerm;
    }

}
