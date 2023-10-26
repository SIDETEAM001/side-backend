package com.sideteam.groupsaver.domain.member.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "MEMBER_ID")
    )
    private Set<MemberRole> roles = new HashSet<>();

    @Builder
    protected Member(String password, String phoneNumber, String nickname, String email, OAuthProvider oAuthProvider,
                     Set<MemberRole> roles) {
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nickname = nickname;
        this.oAuthProvider = oAuthProvider;
        this.roles = roles;
    }

}
