package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private Set<MemberRole> roles = new HashSet<>();

    @Builder
    protected Member(String password, String phoneNumber, String nickname, String email, Set<MemberRole> roles) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.roles = roles;
    }

}
