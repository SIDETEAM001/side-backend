package com.sideteam.groupsaver.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiErrorResponse {
    private final String path;
    private final int statusCode;
    private final String error;
    private final String codeName;
    private final String detail;
    private final String reason;

    public static ResponseEntity<ApiErrorResponse> toResponseEntity(
            @NotNull ErrorCode errorCode,
            Exception exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(of(errorCode, exception, request));
    }

    private static ApiErrorResponse of(
            @NotNull ErrorCode errorCode,
            Exception exception,
            HttpServletRequest request
    ) {
        return ApiErrorResponse.builder()
                .path(request.getServletPath())
                .statusCode(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name())
                .codeName(errorCode.getName())
                .detail(errorCode.getDetail())
                .reason(exception.getMessage())
                .build();
    }

    @Builder
    private ApiErrorResponse(String path, int statusCode, String error, String codeName, String detail, String reason) {
        this.path = path;
        this.statusCode = statusCode;
        this.error = error;
        this.codeName = codeName;
        this.detail = detail;
        this.reason = reason;
    }
}
