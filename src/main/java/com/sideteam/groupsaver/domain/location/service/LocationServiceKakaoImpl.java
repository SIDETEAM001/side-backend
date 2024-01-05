package com.sideteam.groupsaver.domain.location.service;

import com.sideteam.groupsaver.domain.location.domain.Location;
import com.sideteam.groupsaver.domain.location.domain.LocationCoordinate;
import com.sideteam.groupsaver.domain.location.dto.kakao.KakaoCoordinate2RegionResponse;
import com.sideteam.groupsaver.domain.location.dto.kakao.KakaoLocationResponse;
import com.sideteam.groupsaver.domain.location.dto.response.LocationResponse;
import com.sideteam.groupsaver.domain.location.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Transactional
@Service
public class LocationServiceKakaoImpl implements LocationService {

    private static final String KAKAO_KEY_PREFIX = "KakaoAK ";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);

    private final LocationRepository locationRepository;

    private final WebClient webClient;
    private final String kakaoApiKey;
    private final URI searchApiUrl;
    private final URI coordinateApiUrl;


    public LocationServiceKakaoImpl(
            LocationRepository locationRepository,
            WebClient webClient,
            @Value("${location.kakao.api-key}") String kakaoApiKey,
            @Value("${location.kakao.search-api}") String searchApiUrl,
            @Value("${location.kakao.coord-api}") String coordinateApiUrl
    ) {
        this.locationRepository = locationRepository;
        this.webClient = webClient;
        this.kakaoApiKey = kakaoApiKey;
        this.searchApiUrl = URI.create(searchApiUrl);
        this.coordinateApiUrl = URI.create(coordinateApiUrl);
    }


    @Override
    public List<LocationResponse> searchByName(String address) {

        List<Location> locations = locationRepository.findAllByNameContains(address);
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationResponse::of)
                    .toList();
        }

        List<LocationResponse> locationResponses = fetchByAddressName(address);
        saveAllLocations(locationResponses);
        return locationResponses;
    }

    @Override
    public List<LocationResponse> searchByCoordinate(Double longitude, Double latitude) {

        List<Location> locations = locationRepository.findByLocationCoordinate(
                LocationCoordinate.of(longitude, latitude));
        if (!locations.isEmpty()) {
            return locations.stream()
                    .map(LocationResponse::of)
                    .toList();
        }

        List<LocationResponse> locationResponses = fetchByCoordinate(longitude, latitude);
        saveAllLocations(locationResponses);
        return locationResponses;
    }


    private void saveAllLocations(List<LocationResponse> locationResponses) {
        locationRepository.saveAll(locationResponses.stream()
                .map(Location::of)
                .toList());
    }

    private List<LocationResponse> fetchByAddressName(String address) {
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
                    .toList();
        }

        return searchByCoordinate(firstAddress.x(), firstAddress.y());
    }

    private List<LocationResponse> fetchByCoordinate(Double longitude, Double latitude) {
        KakaoCoordinate2RegionResponse response = doKakaoGetRequest(
                getCoordinateApiUrl(longitude, latitude), new ParameterizedTypeReference<>() {}
        );

        if (response == null || response.documents() == null) {
            return emptyList();
        }

        return response.documents().stream()
                .filter(Objects::nonNull)
                .map(KakaoCoordinate2RegionResponse.Document::toLocationResponse)
                .toList();
    }

    private <T> T doKakaoGetRequest(URI uri, ParameterizedTypeReference<T> reference) {
        return webClient.get()
                .uri(uri)
                .headers(this::setKakaoRequestHeader)
                .retrieve()
                .bodyToMono(reference)
                .block(REQUEST_TIMEOUT);
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

}
