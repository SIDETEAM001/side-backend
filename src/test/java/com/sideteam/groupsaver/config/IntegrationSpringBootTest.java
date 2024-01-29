package com.sideteam.groupsaver.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

/**
 * 통합 테스트 환경에 맞는 설정을 사용하도록 지원하는 어노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@ActiveProfiles(profiles = "test")
@Testcontainers
@SpringBootTest(classes = TestRedisConfiguration.class)
public @interface IntegrationSpringBootTest {
}
