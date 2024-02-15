package com.sideteam.groupsaver.utils.context;

import com.sideteam.groupsaver.config.TestRedisConfiguration;
import com.sideteam.groupsaver.global.config.QueryDslConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.*;

/**
 * 리포지터리 테스트 환경에 맞는 설정을 사용하도록 지원하는 어노테이션
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // H2 사용 금지
@Import({RedisProperties.class, TestRedisConfiguration.class, QueryDslConfig.class})
public @interface DataJpaTestcontainersTest {
}
