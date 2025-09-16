package com.lee.pet_store.controller;

import com.lee.pet_store.kafka.producer.PetStoreProfilesProducer;
import com.lee.pet_store.model.Customer;
import com.lee.pet_store.service.AccountManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@RestController
public class PetStoreProfilesController {

    private final AccountManagementService accountManagementService;
    private final PetStoreProfilesProducer petStoreProfilesProducer;

    @GetMapping(value="/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("status: Healthy", HttpStatus.OK);
    }

    @GetMapping("/all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        try{
            List<Customer> customers = accountManagementService.getAllCustomers();
            log.info("Controller: Retrieved {} customers: {}", customers.size(), customers);
            return ResponseEntity.ok(customers);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{loyalty-id}}")
    public ResponseEntity<Optional<Customer>> getCustomerByLoyaltyId(@PathVariable("loyalty-id") String loyaltyId){
        try{
            Optional<Customer> customer = accountManagementService.getCustomerByLoyaltyId(loyaltyId);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/send-customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> sendCustomer(@RequestBody Customer customer) {
        log.info("Controller: Received customer: {}", customer);
        try {
             petStoreProfilesProducer.publish(customer);
             log.info("New customer added: {}", customer);
             return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add-customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = accountManagementService.addCustomer(customer);
            log.info("Newcustomeradded: {}", newCustomer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update-customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customerUpdate) {
        try {
            accountManagementService.updateCustomer(customerUpdate);
            return ResponseEntity.ok(customerUpdate);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete-customer/{loyalty-id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("loyalty-id") String loyaltyId) {
        try {
            accountManagementService.deleteCustomerByLoyaltyId(loyaltyId);
            log.info("Controller: Customer with loyalty ID {} has been deleted.", loyaltyId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addPet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping
    public ResponseEntity<Object> updatePet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping
    public ResponseEntity<Object> getPet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @DeleteMapping
    public ResponseEntity<Object> deletePet() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
