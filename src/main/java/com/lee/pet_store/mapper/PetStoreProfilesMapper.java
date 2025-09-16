package com.lee.pet_store.mapper;

import com.lee.pet_store.model.Customer;
import org.springframework.messaging.Message;

public class PetStoreProfilesMapper {

    public static Customer customerMapper(Message<Customer> message) {
        return Customer.builder()
                .loyaltyId(message.getPayload().getLoyaltyId())
                .firstName(message.getPayload().getFirstName())
                .lastName(message.getPayload().getLastName())
                .phoneNumber(message.getPayload().getPhoneNumber())
                .address(message.getPayload().getAddress())
                .pets(message.getPayload().getPets())
                .balance(message.getPayload().getBalance())
                .build();
    }
}
