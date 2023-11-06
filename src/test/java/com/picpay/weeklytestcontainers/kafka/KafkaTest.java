package com.picpay.weeklytestcontainers.kafka;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@TestPropertySource(
  properties = {
    "spring.kafka.consumer.auto-offset-reset=earliest"
  }
)
@Testcontainers(disabledWithoutDocker = true)
class KafkaTest {

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("docker.io/confluentinc/cp-kafka:7.3.3"));

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Test
    void testKafka()
            throws Exception {
        final String payload = "payload";

        producer.send(payload);

        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertEquals(payload, consumer.getPayload());
        consumer.stopConsumer();
    }
}
