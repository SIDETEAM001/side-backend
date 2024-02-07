package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.LocationData;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.location.LocationErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ActiveProfiles(profiles = "test")
@ContextConfiguration(
        initializers = {ConfigDataApplicationContextInitializer.class},
        classes = {LocationKakaoClientTest.class})
class LocationKakaoClientTest {

    private LocationClient locationClient;


    @Value("${location.kakao.api-key}")
    private String kakaoApiKey;
    @Value("${location.kakao.search-api}")
    private String searchApiUrl;
    @Value("${location.kakao.coord-api}")
    private String coordinateApiUrl;

    @BeforeEach
    void setup() {
        locationClient = new LocationKakaoClient(
                WebClient.builder().build(),
                kakaoApiKey,
                searchApiUrl,
                coordinateApiUrl
        );
    }


    @DisplayName("주소로 검색 테스트")
    @CsvFileSource(files = "src/test/resources/location/search-by-address-response.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void fetchByAddressName(String searchAddress,
                      @ConvertWith(LocationSplitter.class) List<LocationData> expected) {
        List<LocationData> locations = locationClient.fetchByAddressName(searchAddress);

        log.debug("{}: {}", searchAddress, locations);
        assertThat(locations).isEqualTo(expected);
    }

    @DisplayName("좌표로 검색 테스트")
    @CsvFileSource(files = "src/test/resources/location/search-by-coordinate-response.csv", numLinesToSkip = 1)
    @ParameterizedTest
    void fetchByCoordinate(Double longitude, Double latitude,
                            @ConvertWith(LocationSplitter.class) List<LocationData> expected) {

        List<LocationData> locations = locationClient.fetchByCoordinate(longitude, latitude);

        assertThat(locations).isEqualTo(expected);
    }

    @DisplayName("지원하지 않는 좌표 에러 테스트")
    @Test
    void givenUnsupportedCoordinate_whenFetchByCoordinate_thenThrowException() {
        Double longitude = 12.23;
        Double latitude = 23.23;
        Assertions.assertThrows(BusinessException.class, () ->
                locationClient.fetchByCoordinate(longitude, latitude), LocationErrorCode.FAILED_API_REQUEST.getDetail());
    }

}
