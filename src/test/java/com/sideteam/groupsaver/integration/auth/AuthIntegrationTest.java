package com.sideteam.groupsaver.integration.auth;

import com.sideteam.groupsaver.domain.auth.dto.request.LoginRequest;
import com.sideteam.groupsaver.domain.auth.dto.request.SignupRequest;
import com.sideteam.groupsaver.domain.auth.dto.request.TokenRefreshRequest;
import com.sideteam.groupsaver.domain.auth.dto.response.SignupResult;
import com.sideteam.groupsaver.domain.auth.dto.response.TokenDto;
import com.sideteam.groupsaver.domain.auth.service.AuthSignupService;
import com.sideteam.groupsaver.domain.auth.service.AuthTokenService;
import com.sideteam.groupsaver.domain.member.repository.MemberRepository;
import com.sideteam.groupsaver.global.auth.refresh_token.RefreshTokenRepository;
import com.sideteam.groupsaver.utils.context.IntegrationSpringBootTest;
import com.sideteam.groupsaver.utils.provider.MemberProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@IntegrationSpringBootTest
class AuthIntegrationTest {

    @Autowired
    private AuthSignupService authSignupService;
    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @BeforeEach
    void setupSignup() {
        log.debug("시작: 테스트를 위한 회원가입");
        final String nickname = "TestName";
        final String email = "1@test.com";
        final String password = "123456789!@#$a";
        final SignupRequest signupRequest = MemberProvider.createSignupRequest(nickname, email, password);
        final SignupResult signupResult = authSignupService.signup(signupRequest);

        assertThat(signupResult.nickname()).isEqualTo(nickname);
        log.debug("종료: 테스트를 위한 회원가입");
    }

    @AfterEach
    void tearDown() {
        log.debug("시작: 데이터베이스 초기화");
        refreshTokenRepository.deleteAll();
        memberRepository.deleteAll();
        log.debug("종료: 데이터베이스 초기화");
    }


    @Test
    @DisplayName("중복되는 이메일로 회원가입 시도 후, 종복으로 인해 발생하는 예외 확인")
    void givenInvalidMemberInput_whenSignup_thenReturnSignupResponse() {
        // given
        final String nickname = "test1";
        final String duplicatedEmail = "1@test.com";
        final String password = "111111!q";
        final SignupRequest signupRequest = MemberProvider.createSignupRequest(nickname, duplicatedEmail, password);

        // when
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            authSignupService.signup(signupRequest);
        }, "비정상 회원가입 시도가 정상적으로 처리됨!");

    }

    @Test
    @DisplayName("비정상적인 비밀번호로 로그인 시도 후, BadCredentialsException 예외 확인")
    void givenWrongPassword_whenMemberLogin_thenReturnWrongPasswordError() {
        // given
        final String email = "1@test.com";
        final String wrongPassword = "wrong111111!q";

        // when
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            authTokenService.login(new LoginRequest(email, wrongPassword));
        }, "비정상 로그인이 에러를 던지지 않고 정상적으로 로그인됨!");
    }

    @Test
    @DisplayName("정상적인 로그인 후, 리프레시 토큰을 Refresh 요청")
    void givenValidLogin_whenRefresh_thenReturnNewRefreshToken() {
        // given
        final String email = "1@test.com";
        final String password = "123456789!@#$a";
        final TokenDto tokenDto = authTokenService.login(new LoginRequest(email, password));
        final TokenRefreshRequest refreshRequest = new TokenRefreshRequest(tokenDto.refreshToken());

        // when
        final TokenDto refreshedTokens = authTokenService.refreshTokens(refreshRequest.refreshToken());

        // then
        // 새로 발급 받은 refresh 토큰이 기존의 refresh 토큰과 다른지 확인
        assertThat(refreshedTokens.refreshToken()).isNotEqualTo(tokenDto.refreshToken());
    }

}
