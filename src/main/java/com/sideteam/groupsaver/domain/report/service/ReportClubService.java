package com.sideteam.groupsaver.domain.report.service;

import com.sideteam.groupsaver.domain.club.domain.Club;
import com.sideteam.groupsaver.domain.club.repository.ClubRepository;
import com.sideteam.groupsaver.domain.report.domain.ReportClub;
import com.sideteam.groupsaver.domain.report.dto.ReportClubRequest;
import com.sideteam.groupsaver.domain.report.repository.ReportClubRepository;
import com.sideteam.groupsaver.domain.slack.SlackService;
import com.sideteam.groupsaver.global.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_IS_SUSPENDED;
import static com.sideteam.groupsaver.global.exception.club.ClubErrorCode.CLUB_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportClubService {
    private final ReportClubRepository reportClubRepository;
    private final ClubRepository clubRepository;
    private final SlackService slackService;

    public void report(ReportClubRequest request) {
        Club club = checkClub(request.getClubId());
        ReportClub reportClub = ReportClub.of(club, request.getCategory(), request.getEtcReason());
        club.addReport(reportClubRepository.save(reportClub));
        if (club.getReports().size() == 3) {
            club.updateIsActive();
        }
        slackService.sendReportClub(reportClub, club.getReports().size());
    }

    private Club checkClub(long clubId) {
        Club club = clubRepository.findByIdOrThrow(clubId);
        if (!club.isActive()) {
            throw new BusinessException(CLUB_IS_SUSPENDED, "정지된 클럽 : " + clubId);
        }
        return club;
    }
}
