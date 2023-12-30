package com.sideteam.groupsaver.domain.location.dto;

import jakarta.validation.constraints.NotNull;

public record CoordinateRequest(
        @NotNull
        Double longitude,
        @NotNull
        Double latitude
) {
}
