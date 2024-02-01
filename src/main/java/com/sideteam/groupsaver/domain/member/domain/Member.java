package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.domain.category.domain.JobMajor;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.join.domain.WantClubCategory;
import com.sideteam.groupsaver.domain.mypage.dto.request.MyInfoUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNullElse;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDate birth;
    private String gender;
    private String profileUrl = "https://sabuzac-bucket.s3.ap-northeast-2.amazonaws.com/user/profile_default_image/profile.png";

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private Set<MemberRole> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column
    private JobMajor jobCategory;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<WantClubCategory> wantClubCategories = new ArrayList<>();

    private boolean ageTerm;
    private boolean serviceTerm;
    private boolean userInfoTerm;
    private boolean locationTerm;

    @Builder
    protected Member(String password, String phoneNumber, String nickname, String email, OAuthProvider oAuthProvider, Set<MemberRole> roles, JobMajor jobCategory, String gender, LocalDate birth, boolean ageTerm, boolean locationTerm, boolean serviceTerm, boolean userInfoTerm) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
        this.roles = roles;
        this.jobCategory = jobCategory;
        this.gender = gender;
        this.birth = birth;
        this.ageTerm = ageTerm;
        this.serviceTerm = serviceTerm;
        this.locationTerm = locationTerm;
        this.userInfoTerm = userInfoTerm;
    }

    public void update(MyInfoUpdateRequest myInfoUpdateRequest) {
        this.nickname = requireNonNullElse(myInfoUpdateRequest.getNickname(), this.nickname);
        this.birth = requireNonNullElse(myInfoUpdateRequest.getBirth(), this.birth);
        this.profileUrl = requireNonNullElse(myInfoUpdateRequest.getUrl(), this.profileUrl);
        this.jobCategory = requireNonNullElse(myInfoUpdateRequest.getJobCategory(), this.jobCategory);
    }


    public void updateWantClubCategory(List<WantClubCategory> categories) {
        this.wantClubCategories.addAll(categories);
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
