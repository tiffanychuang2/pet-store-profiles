package com.lee.pet_store.kafka.producer;

import com.lee.pet_store.model.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PetStoreProfilesProducer {

    @Value("${spring.kafka.topic.pet-store-topic}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, Customer> kafkaTemplate;

    protected static final String KAFKA_PUBLISHING_EXCEPTION_MESSAGE = "Exception occured while publishing customer";
    protected static final String KAFKA_SUCCESS_EXCEPTION_MESSAGE = "Successfully published customer.";

    public void publish(final Customer customer) {
        log.info("Producer: Received customer: {}", customer);
        try {
            log.info("Producer: Publishing customer: {}", customer);
            kafkaTemplate.send(TOPIC_NAME, customer);
            log.info("Producer: Message sent to topic: " + TOPIC_NAME);
        } catch (Exception e) {
            log.error("{} {} to topic {}", KAFKA_PUBLISHING_EXCEPTION_MESSAGE, e, TOPIC_NAME);
            throw new RuntimeException(KAFKA_PUBLISHING_EXCEPTION_MESSAGE, e);
        }
    }


}
