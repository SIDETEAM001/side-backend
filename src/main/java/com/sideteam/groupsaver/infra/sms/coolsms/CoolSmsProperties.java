package com.sideteam.groupsaver.infra.sms.coolsms;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "sms.coolsms")
public record CoolSmsProperties(
        @NotBlank String apiKey,
        @NotBlank String secretKey,
        @NotBlank String serviceId,
        @NotBlank String senderPhone,
        @NotNull URI smsSendUri
) {
}
