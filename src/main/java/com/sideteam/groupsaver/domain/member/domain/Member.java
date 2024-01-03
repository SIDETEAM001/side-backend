package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.domain.category.domain.ClubCategory;
import com.sideteam.groupsaver.domain.club.domain.ClubMember;
import com.sideteam.groupsaver.domain.category.domain.JobMajor;
import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import com.sideteam.groupsaver.domain.hobby.domain.HobbyMember;
import com.sideteam.groupsaver.domain.join.domain.WantDevelop;
import com.sideteam.groupsaver.domain.join.domain.WantHobby;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullalbe을 해제해도 되는지?
    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    private String birth;
    private String profileUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private Set<MemberRole> roles = new HashSet<>();

    // 직무 & 자기계발 & 취미생활 데이터 받기
    @Column
    private JobMajor jobCategory;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WantDevelop> wantDevelopList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<WantHobby> wantHobbyList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubMember> clubMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<HobbyMember> hobbyMemberList = new ArrayList<>();


    @Builder
    protected Member(String password, String phoneNumber, String nickname, String email, OAuthProvider oAuthProvider, Set<MemberRole> roles, JobMajor jobCategory, List<WantDevelop> wantDevelopList, List<WantHobby> wantHobbyList) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
        this.roles = roles;
        this.jobCategory = jobCategory;
        this.wantDevelopList = wantDevelopList;
        this.wantHobbyList = wantHobbyList;
    }

    public void addAClubMember(ClubMember clubMember) {
        this.getClubMemberList().add(clubMember);
    }

    public void addAHobbyMember(HobbyMember hobbyMember) {
        this.getHobbyMemberList().add(hobbyMember);
    }
}
