package com.lee.pet_store.service;

import com.lee.pet_store.dao.CustomerDAO;
import com.lee.pet_store.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountManagementService {

    private final CustomerDAO customerDAO;

    public AccountManagementService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers() {
        Optional<List<Customer>> customers = customerDAO.getAllCustomers();
        log.info("Retrieved customers = {}", customers);
        return customers.orElseGet(ArrayList::new);
    }

    public Optional<Customer> getCustomerByLoyaltyId(String loyaltyId) {
        Optional<Customer> customer = customerDAO.getCustomerByLoyaltyId(loyaltyId);
        log.info("Found customer: {}", customer);
        return customer;
    }

    public Customer addCustomer(final Customer customer) {
        Customer newCustomer = customerDAO.addCustomer(customer);
        log.info("Added customer: {}", newCustomer);
        return newCustomer;
    }

    public Customer deleteCustomerByLoyaltyId(String loyaltyId) {
        return customerDAO.deleteCustomer(loyaltyId);
    }

    public Customer updateCustomer(Customer customerUpdate) {
        return customerDAO.update(customerUpdate);
    }
}
