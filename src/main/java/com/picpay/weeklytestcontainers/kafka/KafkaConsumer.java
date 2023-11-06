package com.picpay.weeklytestcontainers.kafka;

import static java.util.Optional.ofNullable;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.Lifecycle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final String WEEKLY = "weekly";

    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @Getter
    private String payload;

    private final KafkaListenerEndpointRegistry listenerEndpointRegistry;

    @KafkaListener(topics = WEEKLY, groupId = WEEKLY, id = WEEKLY)
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        latch.countDown();
        this.payload = consumerRecord.value();
    }

    public void stopConsumer() { 
        log.info("stoping consumer: {}", WEEKLY);
        ofNullable(listenerEndpointRegistry.getListenerContainer(WEEKLY))
            .ifPresent(Lifecycle::stop);
    
    }
}
