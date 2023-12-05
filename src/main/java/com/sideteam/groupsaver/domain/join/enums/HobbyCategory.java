package com.sideteam.groupsaver.domain.join.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum HobbyCategory {
    TYPE;

    @JsonCreator
    public static HobbyCategory from(String s) {
        try {
            return HobbyCategory.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HobbyCategory: " + s);
        }
    }
}
