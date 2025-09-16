package com.lee.pet_store.kafka.consumer;

import com.lee.pet_store.mapper.PetStoreProfilesMapper;
import com.lee.pet_store.model.Customer;
import com.lee.pet_store.service.AccountManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PetStoreListener {
    private final AccountManagementService accountManagementService;

    @KafkaListener(topics = "pet-store-profiles", groupId = "pet-store")
    public void listen(Message<Customer> customer) {
        PetStoreProfilesMapper.customerMapper(customer);
        System.out.println("Listener: Received customer: " + customer);
    }
}
