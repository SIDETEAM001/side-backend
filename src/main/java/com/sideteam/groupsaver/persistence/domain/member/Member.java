package com.sideteam.groupsaver.persistence.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String pw;

    private String name;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private boolean isDeleted = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    private Member(String email, String pw, String name, List<String> roles) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.roles = roles;
    }

    public static Member of(String email, String pw, String name, List<String> roles) {
        return new Member(email, pw, name, roles);
    }
}