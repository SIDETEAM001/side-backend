package com.sideteam.groupsaver.infra.sms.coolsms;

import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.infra.sms.SmsClient;
import com.sideteam.groupsaver.infra.sms.coolsms.dto.request.CoolSmsSendRequest;
import com.sideteam.groupsaver.infra.sms.coolsms.dto.response.CoolSmsSendResponse;
import com.sideteam.groupsaver.infra.sms.dto.SmsSendResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import static com.sideteam.groupsaver.global.exception.location.LocationErrorCode.FAILED_API_REQUEST;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@EnableConfigurationProperties(CoolSmsProperties.class)
@RequiredArgsConstructor
@Component
public class SmsCoolSmsClient implements SmsClient {
    // https://coolsms.co.kr

    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final WebClient webClient;
    private final CoolSmsProperties coolSmsProperties;

    @Override
    public SmsSendResult sendTextMessage(String phoneNumber, String message) {
        CoolSmsSendRequest sendRequest = CoolSmsSendRequest.of(coolSmsProperties.senderPhone(), phoneNumber, message);
        CoolSmsSendResponse response = doSendRequest(coolSmsProperties.smsSendUri(), sendRequest, new ParameterizedTypeReference<>() {});

        checkResponse(response);

        return new SmsSendResult(response.to());
    }


    private <T> T doSendRequest(URI uri, CoolSmsSendRequest sendRequest, ParameterizedTypeReference<T> reference) {
        try {
            return webClient.post()
                    .uri(uri)
                    .headers(this::setCoolSmsRequestHeader)
                    .bodyValue(sendRequest)
                    .retrieve()
                    .bodyToMono(reference)
                    .block(REQUEST_TIMEOUT);
        } catch (WebClientResponseException e) {
            log.warn("Failed to send sms, response: {}", e.getResponseBodyAsString());
            throw new BusinessException(FAILED_API_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.warn("Failed to send sms: {}", e.getMessage());
            throw new BusinessException(FAILED_API_REQUEST, e.getMessage());
        }
    }


    private void checkResponse(CoolSmsSendResponse response) {
        if (!response.isSuccess()) {
            log.warn("Failed to send sms in response: {}", response);
            throw new BusinessException(FAILED_API_REQUEST, "Failed to send sms response: " + response);
        }
    }


    private void setCoolSmsRequestHeader(HttpHeaders headers) {
        Instant now = Instant.now();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, createAuthorization(now));
    }

    private String createAuthorization(Instant now) {
        String salt = createSalt();
        String algorithmName = "HMAC-SHA256";
        HmacAlgorithms algorithm = HmacAlgorithms.HMAC_SHA_256;
        String formattedTimestamp = formatTimestamp(now);
        String signature = createSignature(algorithm, coolSmsProperties.secretKey(), now, salt);

        return String.format("%s apiKey=%s, date=%s, salt=%s, signature=%s",
                algorithmName, coolSmsProperties.apiKey(), formattedTimestamp, salt, signature);
    }

    // https://docs.coolsms.co.kr/authentication/api-key API 키 설정 가이드
    private static String createSignature(HmacAlgorithms algorithm, String secretKey, Instant now, String salt) {
        String formattedTimestamp = formatTimestamp(now);
        String rawSignature = formattedTimestamp + salt;

        try {
            Mac mac = Mac.getInstance(algorithm.getName());
            SecretKeySpec secretkeySpec = new SecretKeySpec(secretKey.getBytes(UTF_8), algorithm.getName());
            mac.init(secretkeySpec);

            return new String(Hex.encodeHex(mac.doFinal(rawSignature.getBytes(UTF_8))));
        } catch (Exception e) {
            throw new BusinessException(FAILED_API_REQUEST, e.getMessage());
        }
    }

    private static String createSalt() {
        // 12 ~ 64바이트의 불규칙적이고 랜덤한 문자열
        final int count = (int) (Math.random() * 15) + 12;
        return RandomStringUtils.randomAlphanumeric(count);
    }

    private static String formatTimestamp(Instant now) {
        return DateTimeFormatter.ISO_INSTANT.format(now);
    }

}
