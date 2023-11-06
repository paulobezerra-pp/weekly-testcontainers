package com.picpay.weeklytestcontainers.redis;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class RedisTest {

    private static final int REDIS_PORT = 6379;

    @Container
    static DockerComposeContainer<?> environment = new DockerComposeContainer<>(
            new File("src/test/resources/compose-test.yaml"))
            .withExposedService("redis_1", REDIS_PORT);


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", () -> environment.getServiceHost("redis_1", REDIS_PORT));
        registry.add("spring.data.redis.port", () -> environment.getServicePort("redis_1", REDIS_PORT));
    }

    @Autowired
    private RedisService redisService;

    @Test
    void testRedis() {
        final String value = "Jennifer de Vengerberg";
        final String key = "foo";
        redisService.set(key, value);
        assertEquals(value, redisService.get("foo"));
    }
}
