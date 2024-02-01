package com.sideteam.groupsaver.domain.report.dto;

import com.sideteam.groupsaver.domain.report.domain.ReportClubCategory;
import lombok.Getter;

@Getter
public class ReportClubRequest {
    private long clubId;
    private ReportClubCategory category;
    private String etcReason;
}
