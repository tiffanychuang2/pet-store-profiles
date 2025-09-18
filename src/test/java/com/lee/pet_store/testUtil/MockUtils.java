package com.lee.pet_store.testUtil;

import com.lee.pet_store.dao.CustomerEntity;
import com.lee.pet_store.dao.PetEntity;
import com.lee.pet_store.model.Customer;
import com.lee.pet_store.model.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockUtils {

    // Constants
    static final String ADDRESS_1 = "42 Wallaby Way";
    static final String ADDRESS_2 = "4 Privet Drive";
    static final String ADDRESS_3 = "123 Sesame Street";

    static final int BALANCE = 100;
    static final int BALANCE_1 = 100;
    static final int BALANCE_2 = 0;
    static final int BALANCE_3 = 500;

    static final String CITY_2 = "Little Whinging";

    static final String FIRST_NAME_1 = "Darla";
    static final String FIRST_NAME_2 = "Harry";
    static final String FIRST_NAME_3 = "Lavender";

    static final String LAST_NAME_1 = "Sherman";
    static final String LAST_NAME_2 = "Potter";
    static final String LAST_NAME_3 = "Gooms";

    static final String LOYALTY_ID_1 = "123456789";
    static final String LOYALTY_ID_2 = "0987654321";
    static final String LOYALTY_ID_3 = "2468135790";

    static final String PET_NAME_1 = "Nemo";
    static final String PET_NAME_2 = "Hedwig";
    static final String PET_NAME_3 = "HeiHei";

    static final String PET_TYPE_DOG = "Dog";
    static final String PET_TYPE_BIRD = "Bird";
    static final String PET_TYPE_CAT = "Cat";

    static final String PHONE_NUMBER_1 = "97202678433";
    static final String PHONE_NUMBER_2 = "6146875309";
    static final String PHONE_NUMBER_3 = "8005552368";

    static final String STATE_1 = "AUS";
    static final String STATE_2 = "SUR";

    static final String STREET_AND_NUMBER_1 = "42 Wallaby Way";
    static final String STREET_AND_NUMBER_2 = "4 Privet Drive";

    static final String ZIP_CODE_1 = "12345";
    static final String ZIP_CODE_2 = "54321";

    // Pet builder
    public static Pet mockPet1() {
        return Pet.builder()
                .petType(PET_TYPE_DOG)
                .petName(PET_NAME_1)
                .build();
    }

    // Customer builders
    public static Customer mockCustomer1() {
        return Customer.builder()
                .loyaltyId(LOYALTY_ID_1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .phoneNumber(PHONE_NUMBER_1)
                .address(ADDRESS_1)
                .pets(List.of(mockPet1()))
                .balance(BALANCE_1)
                .build();
    }

    public static Customer mockCustomer2() {
        return Customer.builder()
                .loyaltyId(LOYALTY_ID_2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .phoneNumber(PHONE_NUMBER_2)
                .address(ADDRESS_2)
                .pets(List.of(mockPet1()))
                .balance(BALANCE_2)
                .build();
    }

    public static Customer mockCustomer3() {
        return Customer.builder()
                .loyaltyId(LOYALTY_ID_3)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .phoneNumber(PHONE_NUMBER_3)
                .address(ADDRESS_3)
                .pets(List.of(mockPet1()))
                .balance(BALANCE_3)
                .build();
    }

    public static List<Customer> mockCustomersList = new ArrayList<>(
            Arrays.asList(
                    MockUtils.mockCustomer1(),
                    MockUtils.mockCustomer2(),
                    MockUtils.mockCustomer3()
            )
    );

    public static Customer mockCustomer1UpdateTo3() {
        return Customer.builder()
                .loyaltyId(LOYALTY_ID_1)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .phoneNumber(PHONE_NUMBER_2)
                .address(ADDRESS_3)
                .pets(List.of(mockPet1()))
                .balance(BALANCE_3)
                .build();
    }

    public static CustomerEntity mockCustomerEntity1() {
        return CustomerEntity.builder()
                .loyaltyId(LOYALTY_ID_1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .phoneNumber(PHONE_NUMBER_1)
                .address(ADDRESS_1)
                .pets(List.of(mockPetEntity1()))
                .balance(BALANCE_1)
                .build();
    }

    public static CustomerEntity mockCustomerEntity2() {
        return CustomerEntity.builder()
                .loyaltyId(LOYALTY_ID_2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .phoneNumber(PHONE_NUMBER_2)
                .address(ADDRESS_2)
                .pets(List.of(mockPetEntity1()))
                .balance(BALANCE_2)
                .build();
    }

    public static CustomerEntity mockCustomerEntity3() {
        return CustomerEntity.builder()
                .loyaltyId(LOYALTY_ID_3)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .phoneNumber(PHONE_NUMBER_3)
                .address(ADDRESS_3)
                .pets(List.of(mockPetEntity1()))
                .balance(BALANCE_3)
                .build();
    }

    public static CustomerEntity mockCustomerEntity1UpdateTo3() {
        return CustomerEntity.builder()
                .loyaltyId(LOYALTY_ID_1)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .phoneNumber(PHONE_NUMBER_2)
                .address(ADDRESS_3)
                .pets(List.of(mockPetEntity1()))
                .balance(BALANCE_3)
                .build();
    }

    public static List<CustomerEntity> mockCustomerEntitiesList =
            new ArrayList<>(Arrays.asList(
                    MockUtils.mockCustomerEntity1(),
                    MockUtils.mockCustomerEntity2(),
                    MockUtils.mockCustomerEntity3()
            ));

    public static PetEntity mockPetEntity1() {
        return PetEntity.builder()
                .petType(PET_TYPE_DOG)
                .petName(PET_NAME_1)
                .build();
    }
}


