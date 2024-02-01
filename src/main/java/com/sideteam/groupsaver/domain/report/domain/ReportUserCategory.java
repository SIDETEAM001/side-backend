package com.sideteam.groupsaver.domain.report.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ReportUserCategory {
    USER_REPORT_ISSUE_1("지나친 욕설과 비방"),
    USER_REPORT_ISSUE_2("지나친 개인 정보 요구"),
    USER_REPORT_ISSUE_3("음란 / 성적 게시글, 게시물 기재"),
    USER_REPORT_ISSUE_4("모임 활동과 관계없는 행위"),
    USER_ETC("기타"),
    ;
    private final String issue;
}
