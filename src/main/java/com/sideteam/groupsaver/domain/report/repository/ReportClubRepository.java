package com.sideteam.groupsaver.domain.report.repository;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.report.domain.ReportClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportClubRepository extends JpaRepository<ReportClub, Long> {
}
