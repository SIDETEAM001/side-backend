package com.sideteam.groupsaver.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestRedisConfiguration {

    private static final String REDIS_DOCKER_IMAGE = "redis:7.2.0-alpine";

    public TestRedisConfiguration(RedisProperties redisProperties) {

        GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse(REDIS_DOCKER_IMAGE))
                .withExposedPorts(redisProperties.getPort())
                .withReuse(true);

        redisContainer.start();

        redisProperties.setHost(redisContainer.getHost());
        redisProperties.setPort(redisContainer.getFirstMappedPort());
    }

}
