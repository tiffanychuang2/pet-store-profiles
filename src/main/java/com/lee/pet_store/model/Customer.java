package com.lee.pet_store.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Customer {

    @JsonProperty("loyaltyId")
    private final String loyaltyId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @JsonProperty("pets")
    private List<Pet> pets;

    @JsonProperty("balance")
    private int balance;

}
