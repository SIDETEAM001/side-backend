package com.sideteam.groupsaver.domain.slack;

import com.sideteam.groupsaver.domain.report.domain.ReportClub;
import com.sideteam.groupsaver.domain.report.domain.ReportUser;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SlackService {
    private final String reportURL;
    private static final String LINE_SEPARATOR = "---------------------------------------------------------\n";
    private static final String LINE_FOOTER = "---------------------------------------------------------";

    public SlackService(@Value("${slack.report}") String reportURL) {
        this.reportURL = reportURL;
    }

    public void sendReportClub(ReportClub reportClub, int reportNum) {
        String message =
                LINE_SEPARATOR +
                "클럽 신고가 접수되었습니다 \n" +
                "클럽 Id : " + reportClub.getClub().getId() + "\n" +
                "클럽 이름 : " + reportClub.getClub().getName() + "\n" +
                "신고자 Id : " + reportClub.getCreator().getId() + "\n" +
                "신고자 이메일 : " + reportClub.getCreator().getEmail() + "\n" +
                "신고 내용 : " + reportClub.getCategory().getIssue() + "\n" +
                "누적된 신고 횟수 : " + reportNum + "\n" +
                LINE_FOOTER;
        sendMessageToSlack(message, reportURL);
    }

    public void sendReportUser(ReportUser reportUser, int reportNum) {
        String message =
                LINE_SEPARATOR +
                        "회원 신고가 접수되었습니다 \n" +
                        "회원 Id : " + reportUser.getReportedMember().getId() + "\n" +
                        "회원 이메일 : " + reportUser.getReportedMember().getEmail() + "\n" +
                        "신고자 Id : " + reportUser.getCreator().getId() + "\n" +
                        "신고자 이메일 : " + reportUser.getCreator().getEmail() + "\n" +
                        "신고 내용 : " + reportUser.getCategory().getIssue() + "\n" +
                        "누적된 신고 횟수 : " + reportNum + "\n" +
                        LINE_FOOTER;
        sendMessageToSlack(message, reportURL);
    }

    private void sendMessageToSlack(String message, String slackChannel) {
        new SlackApi(slackChannel).call(new SlackMessage(message));
    }
}
