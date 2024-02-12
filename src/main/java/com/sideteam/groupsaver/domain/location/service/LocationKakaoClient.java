package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.dto.LocationData;
import com.sideteam.groupsaver.domain.location.dto.kakao.KakaoCoordinate2RegionResponse;
import com.sideteam.groupsaver.domain.location.dto.kakao.KakaoLocationResponse;
import com.sideteam.groupsaver.global.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static com.sideteam.groupsaver.global.exception.location.LocationErrorCode.FAILED_API_CONNECTION;
import static com.sideteam.groupsaver.global.exception.location.LocationErrorCode.FAILED_API_REQUEST;
import static java.util.Collections.emptyList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class LocationKakaoClient implements LocationClient {

    private static final String KAKAO_KEY_PREFIX = "KakaoAK ";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);


    private final WebClient webClient;
    private final String kakaoApiKey;
    private final URI searchApiUrl;
    private final URI coordinateApiUrl;

    public LocationKakaoClient(
            WebClient webClient,
            @Value("${location.kakao.api-key}") String kakaoApiKey,
            @Value("${location.kakao.search-api}") String searchApiUrl,
            @Value("${location.kakao.coord-api}") String coordinateApiUrl
    ) {
        this.webClient = webClient;
        this.kakaoApiKey = kakaoApiKey;
        this.searchApiUrl = URI.create(searchApiUrl);
        this.coordinateApiUrl = URI.create(coordinateApiUrl);
    }

    @Override
    public List<LocationData> fetchByAddressName(String address) {
        KakaoLocationResponse response = doKakaoGetRequest(
                getSearchApiUrl(address), new ParameterizedTypeReference<>() {}
        );

        if (response == null || response.documents() == null || response.documents().isEmpty()) {
            return emptyList();
        }

        KakaoLocationResponse.Document firstAddress = response.documents().get(0);

        if (firstAddress.hasRegion3Value()) {
            return response.documents().stream()
                    .filter(Objects::nonNull)
                    .map(KakaoLocationResponse.Document::toLocationResponse)
                    .distinct()
                    .toList();
        }

        return fetchByCoordinate(firstAddress.x(), firstAddress.y());
    }

    @Override
    public List<LocationData> fetchByCoordinate(Double longitude, Double latitude) {
        KakaoCoordinate2RegionResponse response = doKakaoGetRequest(
                getCoordinateApiUrl(longitude, latitude), new ParameterizedTypeReference<>() {}
        );

        if (response == null || response.documents() == null) {
            return emptyList();
        }

        return response.documents().stream()
                .filter(Objects::nonNull)
                .map(KakaoCoordinate2RegionResponse.Document::toLocationResponse)
                .distinct()
                .toList();
    }

    private <T> T doKakaoGetRequest(URI uri, ParameterizedTypeReference<T> reference) {
        try {
            return webClient.get()
                    .uri(uri)
                    .headers(this::setKakaoRequestHeader)
                    .retrieve()
                    .bodyToMono(reference)
                    .block(REQUEST_TIMEOUT);
        } catch (WebClientResponseException e) {
            handleKakaoRestException(uri, e);
        } catch (Exception e) {
            throw new BusinessException(FAILED_API_REQUEST, e.getMessage());
        }
        throw new AssertionError("This code should never be reached");
    }


    private void setKakaoRequestHeader(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, KAKAO_KEY_PREFIX + kakaoApiKey);
    }

    private URI getSearchApiUrl(String address) {
        return UriComponentsBuilder.fromUri(searchApiUrl)
                .queryParam("query", address)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }

    private URI getCoordinateApiUrl(Double longitude, Double latitude) {
        return UriComponentsBuilder.fromUri(coordinateApiUrl)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .build()
                .toUri();
    }

    private void handleKakaoRestException(URI uri, WebClientResponseException e) {
        log.warn("Kakao API request failed. uri: {}, status: {}, body: {}", uri, e.getStatusCode(), e.getResponseBodyAsString());
        if (e.getStatusCode().is4xxClientError()) {
            throw new BusinessException(FAILED_API_REQUEST, e.getResponseBodyAsString());
        }
        if (e.getStatusCode().is5xxServerError()) {
            throw new BusinessException(FAILED_API_CONNECTION, e.getResponseBodyAsString());
        }
    }

}
