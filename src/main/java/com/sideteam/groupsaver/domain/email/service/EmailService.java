package com.sideteam.groupsaver.domain.email.service;

import com.sideteam.groupsaver.domain.email.dto.EmailRequestDto;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.exception.BusinessException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.sideteam.groupsaver.global.exception.member.MemberErrorCode.EMAIL_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    public void sendAuthCode(EmailRequestDto emailRequestDto) throws MessagingException {
        if (!memberRepository.existsByEmail(emailRequestDto.getEmail())) {
            throw new BusinessException(EMAIL_NOT_FOUND, "존재하지 않는 이메일 : " + emailRequestDto.getEmail());
        }
        String code = UUID.randomUUID().toString().substring(0, 6);
        MimeMessage message = createMessage(emailRequestDto.getEmail(), code);
        javaMailSender.send(message);
    }

    private MimeMessage createMessage(String email, String code) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("사부작 인증 코드");
        message.setText(
                "안녕하세요 "+ email +" 님!\n"+
                        "다음은 사부작의 계정을 인증을 위한 인증코드입니다.\n\n"+
                        "인증코드 : "+ code + "\n\n");
        message.setFrom("sabuzacteam@gmail.com");
        return message;
    }
}
