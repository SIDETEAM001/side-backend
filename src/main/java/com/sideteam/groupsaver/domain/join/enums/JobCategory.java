package com.sideteam.groupsaver.domain.join.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum JobCategory {
    TYPE;

    @JsonCreator
    public static JobCategory from(String s){
        try {
            return JobCategory.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid JobCategory: " + s);
        }
    }
}
