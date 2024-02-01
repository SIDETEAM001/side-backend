package com.sideteam.groupsaver.domain.report.dto;

import com.sideteam.groupsaver.domain.report.domain.ReportUserCategory;
import lombok.Getter;

@Getter
public class ReportUserRequest {
    private long reportedMemberId;
    private ReportUserCategory category;
    private String etcReason;
}
