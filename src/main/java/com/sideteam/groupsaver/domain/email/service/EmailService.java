package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.domain.EmailMessage;
import com.sideteam.groupsaver.domain.email.dto.request.CheckEmailCodeRequest;
import com.sideteam.groupsaver.domain.email.dto.request.EmailRequest;
import com.sideteam.groupsaver.domain.email.dto.response.FindEmailResponse;
import com.sideteam.groupsaver.domain.email.repository.EmailRepository;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.emailAuthCode.AuthCodeService;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import static com.sideteam.groupsaver.global.exception.member.MemberErrorCode.EMAIL_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final MemberRepository memberRepository;
    private final AuthCodeService authCodeService;

    public void sendAuthCode(EmailRequest emailRequestDto) throws MessagingException, IOException {
        if (!memberRepository.existsByEmail(emailRequestDto.getEmail())) {
            throw new BusinessException(EMAIL_NOT_FOUND, "존재하지 않는 이메일 : " + emailRequestDto.getEmail());
        }
        javaMailSender.send(createMessage(emailRequestDto.getEmail(), authCodeService.setCode(emailRequestDto.getEmail())));
    }

    public FindEmailResponse checkAuthCode(CheckEmailCodeRequest codeRequest) {
        authCodeService.checkCode(codeRequest.getEmail(), codeRequest.getCode());
        return findEmail(codeRequest.getEmail());
    }

    private MimeMessage createMessage(String email, String code) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("사부작 계정 인증 코드");
        message.setText(
                "안녕하세요 "+ email +" 님!\n"+
                        "다음은 사부작의 계정 인증을 위한 인증코드입니다.\n\n"+
                        "인증코드 : "+ code + "\n\n");
        message.setFrom("sabuzacteam@gmail.com");
        emailRepository.save(EmailMessage.of(email, message.getSubject(), message.getContent().toString()));
        return message;
    }

    private FindEmailResponse findEmail(String email) {
        LocalDate createAt = memberRepository.findCreateAtByEmail(email)
                .atZone(ZoneId.systemDefault()).toLocalDate();
        return FindEmailResponse.of(email, createAt);
    }
}
