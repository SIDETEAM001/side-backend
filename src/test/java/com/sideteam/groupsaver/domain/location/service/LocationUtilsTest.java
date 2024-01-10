package com.sideteam.groupsaver.domain.location.service;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LocationUtilsTest {

    @Test
    void givenCoordinate_whenCalculateDistance_then() {
        final double distance = LocationUtils.calculateHaversineDistanceKm(37.547889, 126.997128, 35.158874, 129.043846);
//        final double distance = LocationUtils.calculateHaversineDistanceKm(37.53463, 127.00055,37.5666,126.9783);

        final Offset<Double> offset = Offset.offset(1E-5);
        assertThat(distance).isEqualTo(322.72225, offset);
    }

}
