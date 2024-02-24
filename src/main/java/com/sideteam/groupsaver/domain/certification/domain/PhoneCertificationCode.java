package com.sideteam.groupsaver.domain.certification.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;

@RedisHash(value = "phone_certification_code")
@Getter
public class PhoneCertificationCode {

    @Id
    private String phoneNumber;

    private String certificationCode;

    @TimeToLive
    private long timeToLive;


    public static PhoneCertificationCode of(final String phoneNumber, final String certificationCode, Duration timeToLive) {
        PhoneCertificationCode phoneCertificationCode = new PhoneCertificationCode();
        phoneCertificationCode.phoneNumber = PhoneNumber.format(phoneNumber);
        phoneCertificationCode.certificationCode = certificationCode;
        phoneCertificationCode.timeToLive = timeToLive.toSeconds();
        return phoneCertificationCode;
    }

}
