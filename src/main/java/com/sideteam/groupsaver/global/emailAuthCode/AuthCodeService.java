package com.sideteam.groupsaver.global.emailAuthCode;

import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static com.sideteam.groupsaver.global.exception.auth.AuthErrorCode.AUTH_CODE_IS_NULL;
import static com.sideteam.groupsaver.global.exception.auth.AuthErrorCode.FAILED_AUTH_CODE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthCodeService {
    private final AuthCodeRepository authCodeRepository;

    public String setCode(String email) {
        if (authCodeRepository.existsById(email)) {
            deleteCode(email);
        }
        String code = UUID.randomUUID().toString().substring(0, 6);
        log.info("이메일 인증 코드 저장 : {} / {}", email, code);
        return authCodeRepository.save(AuthCode.of(email, code, 3)).getCode();
    }

    public void checkCode(String email, String code) {
        AuthCode authCode = authCodeRepository.findById(email).orElseThrow(() ->
                new BusinessException(AUTH_CODE_IS_NULL, "해당 이메일에 저장된 인증코드가 없습니다 : " + email)
        );
        if (Objects.equals(authCode.getCode(), code)) {
            deleteCode(email);
        }
        else {
            throw new BusinessException(FAILED_AUTH_CODE, "잘못된 인증코드입니다");
        }
    }

    private void deleteCode(String email) {
        authCodeRepository.deleteById(email);
    }
}
