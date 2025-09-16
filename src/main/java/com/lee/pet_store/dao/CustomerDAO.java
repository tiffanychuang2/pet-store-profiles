package com.lee.pet_store.dao;

import com.lee.pet_store.model.Customer;
import com.lee.pet_store.model.Pet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomerDAO {

    private final CustomerRepository customerRepository;

    public Optional<List<Customer>> getAllCustomers() {
        List<CustomerEntity> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);
        log.info("DAO: Retrieved {} customers: {} from customerRepository",  entityListToCustomerList(customers).size(), customers);
        return Optional.of(entityListToCustomerList(customers));
    }

    public Optional<Customer> getCustomerByLoyaltyId(String loyaltyId) {
        CustomerEntity customer = customerRepository.findByLoyaltyId(loyaltyId);
        log.info("DAO: getCustomerByLoyaltyId retrieved customer with loyalty ID: {}: {}", loyaltyId, customer);
        return Optional.of(entityToCustomer(customer));
    }

    public Customer addCustomer(Customer customer) {
        CustomerEntity newCustomer = customerRepository.save(customerToEntity(customer));
        log.info("DAO: Added new customer: {}", newCustomer);
        return customer;
    }

    public Customer deleteCustomer(String loyaltyId) {
        CustomerEntity customerToDelete = customerRepository.findByLoyaltyId(loyaltyId);
        log.info("DAO: Deleting customer: {}", customerToDelete);
        customerRepository.delete(customerToDelete);
        log.info("DAO: Deleted customer: {}", customerToDelete);
        return entityToCustomer(customerToDelete);
    }

    public CustomerEntity setCustomerToUpdatedCustomer(CustomerEntity customer, Customer customerUpdate) {
        log.info("DAO: Setting customer to updated customer: {}", customer.toString());
        customerToEntity(customerUpdate);
        List<PetEntity> updatedPetEntityList = petListToEntityList(customerUpdate.getPets());
        customer.setFirstName(customerUpdate.getFirstName());
        customer.setLastName(customerUpdate.getLastName());
        customer.setPhoneNumber(customerUpdate.getPhoneNumber());
        customer.setAddress(customerUpdate.getAddress());
        customer.setAddress(customerUpdate.getAddress());
        customer.setPets(updatedPetEntityList);
        customer.setBalance(customerUpdate.getBalance());
        log.info("DAO: Customer updated: {}", entityToCustomer(customer).toString());
        return customer;
    }

    public Customer update(Customer customerUpdate) {
        String loyaltyId = customerUpdate.getLoyaltyId();
        CustomerEntity customer = Optional.ofNullable(customerRepository.findByLoyaltyId(loyaltyId))
                .map(foundCustomer -> {
                    log.info("DAO: Found customer: {}", entityToCustomer(foundCustomer).toString());
                    customerRepository.delete(foundCustomer);
                    customerRepository.save(customerToEntity(customerUpdate));
                    log.info("DAO: Customer update saved: {}", entityToCustomer(foundCustomer).toString());
                    return foundCustomer;
                })
                .orElseThrow(() -> new NoSuchElementException("Customer with loyalty ID " + loyaltyId + " not found."));
        log.info("DAO: Customer updated and saved: {}",  entityToCustomer(customer).toString());
        return entityToCustomer(customer);
    }

    private CustomerEntity customerToEntity(Customer customer) {
        return CustomerEntity.builder()
                .loyaltyId(customer.getLoyaltyId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .pets(petListToEntityList(customer.getPets()))
                .balance(customer.getBalance())
                .build();
    }

    private Customer entityToCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                .loyaltyId(customerEntity.getLoyaltyId())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .phoneNumber(customerEntity.getPhoneNumber())
                .address(customerEntity.getAddress())
                .pets(entityListToPetList(customerEntity.getPets()))
                .balance(customerEntity.getBalance())
                .build();
    }

    private PetEntity petToEntity(Pet pet) {
        return PetEntity.builder()
                .petName(pet.getPetName())
                .petType(pet.getPetType())
                .build();
    }

    private Pet entityToPet(PetEntity petEntity) {
        return Pet.builder()
                .petName(petEntity.getPetName())
                .petType(petEntity.getPetType())
                .build();
    }

    private List<CustomerEntity> customerListToEntityList(List<Customer> customers) {
//        return customers.stream().map(this::customerToEntity).toList();
        return customers.stream()
                .map(this::customerToEntity)
                .collect(Collectors.toList());
    }

    private List<Customer> entityListToCustomerList(List<CustomerEntity> customerEntityList) {
//        return customerEntityList.stream().map(this::entityToCustomer).toList();
        return customerEntityList.stream()
                .map(this::entityToCustomer)
                .collect(Collectors.toList());
    }

    private List<PetEntity> petListToEntityList(List<Pet> pets) {
//        return pets.stream().map(this::petToEntity).toList;
        return pets.stream()
                .map(this::petToEntity)
                .collect(Collectors.toList());
    }

    private List<Pet>  entityListToPetList(List<PetEntity> petEntityList) {
//        return petEntityList.stream().map(this::entityToPet).toList;
        return petEntityList.stream()
                .map(this::entityToPet)
                .collect(Collectors.toList());
    }

}
