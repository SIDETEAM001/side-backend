package com.sideteam.groupsaver.global.config.swagger;

import com.sideteam.groupsaver.global.auth.AuthConstants;
import com.sideteam.groupsaver.global.util.ProjectTimeFormat;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static com.sideteam.groupsaver.global.util.ProjectTimeFormat.SERVER_ZONE_ID;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.config.Elements.JWT;

@OpenAPIDefinition(
        servers = {
                @Server(url = "${app.backend-api}", description = "Production Server"),
                @Server(url = "http://localhost:8080", description = "Local Server"),
        },
        tags = {
                @Tag(name = "Club", description = "모임"),
        }
)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Configuration
public class SwaggerConfig {

    private final BuildProperties buildProperties;
    private final Environment environment;


    @Bean
    public OpenAPI openAPI() {
        final SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT);

        return new OpenAPI()
                .info(info())
                .addServersItem(new io.swagger.v3.oas.models.servers.Server().url(""))
                .addSecurityItem(securityRequirement)
                .components(securitySchemes());
    }

    @Bean
    public OperationCustomizer disableSwaggerSecurity() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethodAnnotation(DisableSwaggerSecurity.class) != null) {
                operation.setSecurity(Collections.emptyList());
            }
            return operation;
        };
    }


    private Info info() {
        final String activeProfile = getActiveProfile();
        return new Info()
                .title("사부작 API (" + activeProfile + ")")
                .description("API 명세 문서"
                        + "<br> 빌드 일자: " + getFormattedTime(buildProperties.getTime())
                        + "<br> 실행 일자: " + getFormattedTime(Instant.now()))
                .version(buildProperties.getVersion());
    }


    private Components securitySchemes() {
        final SecurityScheme securityScheme = new SecurityScheme()
                .name(JWT)
                .type(SecurityScheme.Type.HTTP)
                .scheme(AuthConstants.TOKEN_TYPE.trim())
                .bearerFormat(JWT)
                .in(SecurityScheme.In.HEADER)
                .name(AUTHORIZATION);

        return new Components()
                .addSecuritySchemes(JWT, securityScheme);
    }

    private String getActiveProfile() {
        if (ObjectUtils.isEmpty(environment.getActiveProfiles())) {
            return environment.getDefaultProfiles()[0];
        }
        return environment.getActiveProfiles()[0];
    }

    private String getFormattedTime(Instant time) {
        return DateTimeFormatter
                .ofPattern("yy-MM-dd E HH:mm:ss zzz")
                .withZone(SERVER_ZONE_ID)
                .format(time);
    }

}
