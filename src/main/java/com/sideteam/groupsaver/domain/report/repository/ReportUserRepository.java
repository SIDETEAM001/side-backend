package com.sideteam.groupsaver.domain.report.repository;

import com.sideteam.groupsaver.domain.report.domain.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportUserRepository extends JpaRepository<ReportUser, Long> {
}
