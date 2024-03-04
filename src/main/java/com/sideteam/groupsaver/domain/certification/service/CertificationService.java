package com.sideteam.groupsaver.domain.certification.service;

import com.sideteam.groupsaver.domain.certification.domain.PhoneCertificationCode;
import com.sideteam.groupsaver.domain.certification.domain.PhoneNumber;
import com.sideteam.groupsaver.domain.certification.dto.response.PhoneSendResponse;
import com.sideteam.groupsaver.domain.certification.dto.response.PhoneVerifyResponse;
import com.sideteam.groupsaver.domain.certification.repository.PhoneCertificationCodeRepository;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import com.sideteam.groupsaver.infra.sms.SmsClient;
import com.sideteam.groupsaver.infra.sms.dto.SmsSendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.sideteam.groupsaver.global.exception.auth.AuthErrorCode.FAILED_AUTH_CODE;

@Slf4j
@RequiredArgsConstructor
@Service
public class CertificationService {

    private static final int CERTIFICATION_CODE_LENGTH = 6;
    private static final Duration CERTIFICATION_CODE_EXPIRATION = Duration.ofMinutes(3);


    private final SmsClient smsClient;
    private final PhoneCertificationCodeRepository phoneCertificationCodeRepository;


    public PhoneSendResponse sendCertificationCode(final String phoneNumber) {
        String certificationCode = generateCertificationCode();
        String message = createMessage(certificationCode);
        PhoneCertificationCode phoneCertificationCode = PhoneCertificationCode.of(phoneNumber, message, CERTIFICATION_CODE_EXPIRATION);

        phoneCertificationCodeRepository.save(phoneCertificationCode);

        SmsSendResult sendResult = smsClient.sendTextMessage(phoneNumber, certificationCode);
        return new PhoneSendResponse(sendResult.phoneNumber(), CERTIFICATION_CODE_EXPIRATION);
    }

    public PhoneVerifyResponse verifyCertificationCode(final String phoneNumber, final String code) {
        String formattedPhoneNumber = PhoneNumber.format(phoneNumber);

        PhoneCertificationCode phoneCertificationCode = phoneCertificationCodeRepository.findById(formattedPhoneNumber)
                .orElseThrow(() -> new AuthErrorException(FAILED_AUTH_CODE, "잘못된 인증번호입니다."));

        if (!phoneCertificationCode.getCertificationCode().equals(code)) {
            log.warn("인증번호가 일치하지 않습니다. phoneNumber: {}", phoneNumber);
            throw new AuthErrorException(FAILED_AUTH_CODE, "잘못된 인증번호입니다.");
        }

        phoneCertificationCodeRepository.deleteById(formattedPhoneNumber);

        return PhoneVerifyResponse.success();
    }


    private static String generateCertificationCode() {
        return RandomStringUtils.randomNumeric(CERTIFICATION_CODE_LENGTH);
    }

    private static String createMessage(String code) {
        return "[사부작] 인증번호 [" + code + "]를 입력해주세요.";
    }


}
