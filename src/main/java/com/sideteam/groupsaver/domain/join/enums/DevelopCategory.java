package com.sideteam.groupsaver.domain.join.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum DevelopCategory {
    TYPE;

    @JsonCreator
    public static DevelopCategory from(String s){
        try {
            return DevelopCategory.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid DevelopCategory: " + s);
        }
    }
}
