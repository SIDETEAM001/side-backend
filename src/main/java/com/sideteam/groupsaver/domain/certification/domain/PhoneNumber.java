package com.sideteam.groupsaver.domain.certification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class PhoneNumber {

    @Column(unique = true, nullable = false, length = 20)
    private String phoneNumber;


    public static PhoneNumber of(String phoneNumberString) {
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.phoneNumber = format(phoneNumberString);
        return phoneNumber;
    }


    public static String format(String phoneNumber) {
        return phoneNumber.replace("-", "").trim();
    }

}
