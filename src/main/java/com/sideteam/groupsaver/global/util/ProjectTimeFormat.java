package com.sideteam.groupsaver.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectTimeFormat {

    public static final String LOCAL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String LOCAL_DATE_PATTERN_EXAMPLE = "2020-01-11";
    public static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String LOCAL_DATE_TIME_PATTERN_EXAMPLE = "2024-11-01 01:23:34";
    public static final String LOCAL_CREATE_DATE_PATTERN = "yyyy.MM.dd";
    public static final String LOCAL_CREATE_DATE_PATTERN_EXAMPLE = "2024.01.11";

    public static final String SERVER_TIMEZONE = "Asia/Seoul";
    public static final ZoneId SERVER_ZONE_ID = ZoneId.of(SERVER_TIMEZONE);


}
