package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.global.util.converter.EnumValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Region1Type implements EnumValue {

    SEOUL("서울", 11, "SEO"),
    BUSAN("부산", 21, "BUS"),
    DAEGU("대구", 22, "DAG"),
    INCHEON("인천", 23, "INC"),
    GWANGJU("광주", 24, "GWA"),
    DAEJEON("대전", 25, "DAJ"),
    ULSAN("울산", 26, "ULS"),
    SEJONG("세종", 29, "SEJ"),
    GYEONGGI("경기", 31, "GYE"),
    GANGWONDO("강원", 32, "GAN"),
    CHUNGBUK("충청북", 33, "CHB"),
    CHUNGNAM("충청남", 34, "CHN"),
    GYUENGBUK("경상북", 35, "GYB"),
    GYUENGNAM("경상남", 36, "GYN"),
    JEONBUK("전라북", 37, "JEB"),
    JEONNAM("전라남", 38, "JEN"),
    JEJU("제주", 39, "JEJ"),

    ;

    private final String name;
    private final Integer regionCode;
    private final String code;

    public static Region1Type of(String inputName) {
        return Arrays.stream(Region1Type.values())
                .filter(region1Type -> inputName.contains(region1Type.name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}