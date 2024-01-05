package com.sideteam.groupsaver.domain.firebase.domain;

import com.sideteam.groupsaver.domain.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String token;

    private FcmToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public static FcmToken of(String email, String token) {
        return new FcmToken(email, token);
    }

    public void updateFcmToken(String token) {
        this.setToken(token);
    }

    public void updateToken(String token) {
        this.setToken(token);
    }
}