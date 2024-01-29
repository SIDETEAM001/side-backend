package com.sideteam.groupsaver.domain.report.service;

import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.domain.MemberActive;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.domain.report.domain.ReportUser;
import com.sideteam.groupsaver.domain.report.dto.ReportUserRequest;
import com.sideteam.groupsaver.domain.report.repository.ReportUserRepository;
import com.sideteam.groupsaver.domain.slack.SlackService;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.sideteam.groupsaver.global.exception.member.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportUserService {
    private final ReportUserRepository reportUserRepository;
    private final MemberRepository memberRepository;
    private final SlackService slackService;


    public void reportUser(ReportUserRequest request) {
        Member member = checkMember(request.getMemberId());
        ReportUser reportUser = ReportUser.of(request.getCategory(), member, request.getEtcReason());
        member.addReport(reportUserRepository.save(reportUser));
        if (member.getReports().size() == 3) {
            member.suspend();
        }
        slackService.sendReportUser(reportUser, member.getReports().size());
    }

    private Member checkMember(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        if (member.getActiveStatus() != MemberActive.ACTIVE) {
            throw new BusinessException(MEMBER_NOT_FOUND, "회원 상태 : " + member.getActiveStatus().getActive());
        }
        return member;
    }
}
