package com.lee.pet_store.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {

    CustomerEntity findByLoyaltyId(String loyaltyId);

    List<CustomerEntity> findAllByPhoneNumber(String phoneNumber);
}
