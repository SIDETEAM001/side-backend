package com.sideteam.groupsaver.infra.sms;

import com.sideteam.groupsaver.infra.sms.coolsms.CoolSmsProperties;
import com.sideteam.groupsaver.infra.sms.coolsms.SmsCoolSmsClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

@EnableConfigurationProperties(CoolSmsProperties.class)
@ExtendWith({SpringExtension.class})
@ActiveProfiles(profiles = "test")
@ContextConfiguration(
        initializers = {ConfigDataApplicationContextInitializer.class},
        classes = {SmsCoolSmsClientTest.class})
class SmsCoolSmsClientTest {

    @Autowired
    private CoolSmsProperties coolSmsProperties;

    private SmsClient smsClient;

    @BeforeEach
    void setup() {
        smsClient = new SmsCoolSmsClient(WebClient.builder().build(), coolSmsProperties);
    }

    @Test
    void sendTextMessage() {
        String phoneNumber = "01029842728";
        String message = "메세지 전송 테스트, 인증번호는 123456입니다.";

        // 돈나가서 보류
//        SmsSendResult sendResult = smsClient.sendTextMessage(phoneNumber, message);
//        assertThat(sendResult.phoneNumber()).isEqualTo(phoneNumber);
    }

}
