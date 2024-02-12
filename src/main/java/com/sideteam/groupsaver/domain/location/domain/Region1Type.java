package com.sideteam.groupsaver.domain.location.domain;

import com.sideteam.groupsaver.global.exception.ApiErrorCode;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.util.converter.EnumValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Region1Type implements EnumValue {

    SEOUL("서울", "서울", 11, "서울"),
    BUSAN("부산", "부산", 21, "부산"),
    DAEGU("대구", "대구", 22, "대구"),
    INCHEON("인천", "인천", 23, "인천"),
    GWANGJU("광주", "광주", 24, "광주"),
    DAEJEON("대전", "대전", 25, "대전"),
    ULSAN("울산", "울산", 26, "울산"),
    SEJONG("세종", "세종", 29, "세종"),
    GYEONGGI("경기", "경기", 31, "경기"),
    GANGWON("강원", "강원", 32, "강원"),
    CHUNGBUK("충북", "충청북도", 33, "충북"),
    CHUNGNAM("충남", "충청남도", 34, "충남"),
    GYUENGBUK("경북", "경상북도", 35, "경북"),
    GYUENGNAM("경남", "경상남도", 36, "경남"),
    JEONBUK("전북", "전라북도", 37, "전북"),
    JEONNAM("전남", "전라남도", 38, "전남"),
    JEJU("제주", "제주", 39, "제주"),

    ;

    private final String name;
    private final String longName;
    private final Integer regionCode;
    private final String code;


    public static boolean matchesRegion1TypeName(String inputName) {
        return Arrays.stream(Region1Type.values())
                .anyMatch(region1Type -> inputName.equals(region1Type.name) || inputName.equals(region1Type.longName));
    }

    public static String removeRegion1InString(String inputName) {
        return Arrays.stream(Region1Type.values())
                .filter(region1Type -> inputName.startsWith(region1Type.name) || inputName.startsWith(region1Type.longName))
                .findFirst()
                .map(region1Type -> removeRegion1InString(inputName, region1Type))
                .orElse(inputName);
    }

    public static String removeRegion1InString(String inputName, Region1Type region1Type) {
        return inputName.replaceFirst(region1Type.name, "").replaceFirst(region1Type.longName, "").trim();
    }

    public static Optional<Region1Type> findStartWithRegion1Name(String inputName) {
        return Arrays.stream(Region1Type.values())
                .filter(region1Type -> inputName.startsWith(region1Type.name) || inputName.startsWith(region1Type.longName))
                .findFirst();
    }


    public static Region1Type of(String inputName) {
        return Arrays.stream(Region1Type.values())
                .filter(region1Type -> matchesRegion1TypeNameOrPrefix(inputName, region1Type))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ApiErrorCode.SERVER_ERROR, inputName + ", 해당 지역이 존재하지 않습니다."));
    }

    private static boolean matchesRegion1TypeNameOrPrefix(String inputName, Region1Type region1Type) {
        return inputName.startsWith(region1Type.name) || inputName.equals(region1Type.longName);
    }

}
