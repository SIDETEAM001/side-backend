package com.sideteam.groupsaver.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuthMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "oauth_provider")
    @Enumerated(value = EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Builder
    protected OAuthMember(String nickname, String email, OAuthProvider oAuthProvider) {
        this.nickname = nickname;
        this.email = email;
        this.oAuthProvider = oAuthProvider;
    }
}