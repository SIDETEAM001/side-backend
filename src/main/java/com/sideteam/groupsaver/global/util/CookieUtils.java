package com.sideteam.groupsaver.global.util;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class CookieUtils {

    public static ResponseCookie createCookie(String name, String value, Duration maxAge) {
        return ResponseCookie.from(name, value)
                .path("/")
                .maxAge(maxAge)
                .httpOnly(true)
                .sameSite("None")
                .build();
    }

}
