package com.sideteam.groupsaver.domain.certification.service;

import com.sideteam.groupsaver.domain.certification.domain.PhoneCertificationCode;
import com.sideteam.groupsaver.domain.certification.dto.VerifyResult;
import com.sideteam.groupsaver.domain.certification.dto.response.PhoneSendResponse;
import com.sideteam.groupsaver.domain.certification.dto.response.PhoneVerifyResponse;
import com.sideteam.groupsaver.domain.certification.repository.PhoneCertificationCodeRepository;
import com.sideteam.groupsaver.global.exception.ErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorCode;
import com.sideteam.groupsaver.global.exception.auth.AuthErrorException;
import com.sideteam.groupsaver.infra.sms.SmsClient;
import com.sideteam.groupsaver.infra.sms.dto.SmsSendResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
class CertificationServiceTest {

    private CertificationService certificationService;

    @Mock
    private SmsClient smsClient;
    @Mock
    private PhoneCertificationCodeRepository phoneCertificationCodeRepository;


    @BeforeEach
    void setup() {
        certificationService = new CertificationService(smsClient, phoneCertificationCodeRepository);
    }

    @DisplayName("인증번호를 설정한 번호로 전송 성공")
    @Test
    void sendCertificationCode() {
        // given
        final String phoneNumber = "01012345678";
        given(smsClient.sendTextMessage(eq(phoneNumber), any())).willReturn(new SmsSendResult(phoneNumber));
        given(phoneCertificationCodeRepository.save(any())).willReturn(any());

        // when
        PhoneSendResponse sendResponse = certificationService.sendCertificationCode(phoneNumber);

        // then
        assertThat(sendResponse.phoneNumber()).isEqualTo(phoneNumber);
    }

    @DisplayName("인증번호 확인 성공")
    @Test
    void givenCode_whenVerifyPhone_thenReturnSuccess() {
        // given
        final String phoneNumber = "01012345678";
        final String code = "123456";
        final PhoneCertificationCode certificationCode = PhoneCertificationCode.of(phoneNumber, code, Duration.ofMinutes(3));
        given(phoneCertificationCodeRepository.findById(phoneNumber)).willReturn(Optional.of(certificationCode));

        // when
        PhoneVerifyResponse verifyResponse = certificationService.verifyCertificationCode(phoneNumber, code);

        // then
        assertThat(verifyResponse.status()).isEqualTo(VerifyResult.SUCCESS);
    }

    @DisplayName("불일치하는 인증번호 실패 테스트")
    @Test
    void givenWrongCode_whenVerifyPhone_thenReturnFail() {
        // given
        final String phoneNumber = "01012345678";
        final String code = "123456";
        final String wrongCode = "654321";
        final PhoneCertificationCode certificationCode = PhoneCertificationCode.of(phoneNumber, code, Duration.ofMinutes(3));
        given(phoneCertificationCodeRepository.findById(phoneNumber)).willReturn(Optional.of(certificationCode));

        // when
        ErrorCode errorCode = Assertions.assertThrows(AuthErrorException.class, () ->
                        certificationService.verifyCertificationCode(phoneNumber, wrongCode),
                "불일치하는 인증 코드는 거부해야 하지만, 성공으로 잘못 처리됨!").getErrorCode();

        // then
        assertThat(errorCode).isEqualTo(AuthErrorCode.FAILED_AUTH_CODE);
    }

}
