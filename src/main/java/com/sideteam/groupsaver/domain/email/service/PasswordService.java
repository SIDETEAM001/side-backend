package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.domain.EmailMessage;
import com.sideteam.groupsaver.domain.email.dto.request.ChangePwRequest;
import com.sideteam.groupsaver.domain.email.dto.request.CheckPwEmailCodeRequest;
import com.sideteam.groupsaver.domain.email.dto.request.PwRequest;
import com.sideteam.groupsaver.domain.email.repository.EmailRepository;
import com.sideteam.groupsaver.domain.member.domain.Member;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.emailAuthCode.AuthCodeService;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.sideteam.groupsaver.global.exception.member.MemberErrorCode.EMAIL_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordService {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final MemberRepository memberRepository;
    private final AuthCodeService authCodeService;
    private final PasswordEncoder passwordEncoder;

    public void sendEmail(PwRequest pwRequest) throws MessagingException, IOException {
        if (!memberRepository.existsByEmail(pwRequest.getEmail())) {
            throw new BusinessException(EMAIL_NOT_FOUND, "존재하지 않는 이메일 : " + pwRequest.getEmail());
        }
        javaMailSender.send(createMessage(pwRequest.getEmail(), authCodeService.setCode(pwRequest.getEmail())));
    }

    public void checkCode(CheckPwEmailCodeRequest codeRequest) {
        authCodeService.checkCode(codeRequest.getEmail(), codeRequest.getCode());
    }

    public void changePassword(ChangePwRequest pwRequest) {
        memberRepository.updatePasswordByEmail(pwRequest.getEmail(), passwordEncoder.encode(pwRequest.getPassword()));
    }

    private MimeMessage createMessage(String email, String code) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("사부작 비밀번호 변경을 위한 인증 코드");
        message.setText(
                "안녕하세요 "+ email +" 님!\n"+
                        "다음은 사부작의 계정 인증을 위한 인증코드입니다.\n\n"+
                        "인증코드 : "+ code + "\n\n");
        message.setFrom("sabuzacteam@gmail.com");
        emailRepository.save(EmailMessage.of(email, message.getSubject(), message.getContent().toString()));
        return message;
    }
}
