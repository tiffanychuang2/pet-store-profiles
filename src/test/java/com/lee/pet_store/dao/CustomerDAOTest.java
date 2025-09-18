package com.lee.pet_store.dao;

import com.lee.pet_store.model.Customer;
import com.lee.pet_store.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.lee.pet_store.testUtil.MockUtils.mockCustomer1;
import static com.lee.pet_store.testUtil.MockUtils.mockCustomer1UpdateTo3;
import static com.lee.pet_store.testUtil.MockUtils.mockCustomerEntitiesList;
import static com.lee.pet_store.testUtil.MockUtils.mockCustomerEntity1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerDAOTest {

    private CustomerRepository customerRepository;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerDAO = new CustomerDAO(customerRepository);
    }

    @Nested
    class HappyPath {

        @Test
        void getAllCustomers_returns_list_of_customers() {
            List<CustomerEntity> expectedCustomersEntityList = mockCustomerEntitiesList;
            when(customerRepository.findAll()).thenReturn(expectedCustomersEntityList);
            List<Customer> expectedCustomersList = mockEntityListToCustomerList(expectedCustomersEntityList);

            Optional<List<Customer>> actualCustomers = customerDAO.getAllCustomers();

            assertTrue(actualCustomers.isPresent());
            assertEquals(expectedCustomersList, actualCustomers.get());
            verify(customerRepository).findAll();
        }

        @Test
        void getCustomerByLoyaltyId_returns_customer() {
            CustomerEntity expectedEntity = mockCustomerEntity1();
            String loyaltyId = expectedEntity.getLoyaltyId();
            Customer expectedCustomer = mockEntityToCustomer(expectedEntity);

            when(customerRepository.findByLoyaltyId(loyaltyId)).thenReturn(expectedEntity);

            Optional<Customer> actualCustomer = customerDAO.getCustomerByLoyaltyId(loyaltyId);

            assertTrue(actualCustomer.isPresent());
            assertEquals(expectedCustomer, actualCustomer.get());
            verify(customerRepository).findByLoyaltyId(loyaltyId);
        }

        @Test
        void addCustomer_adds_new_customer_to_repository() {
            Customer newCustomer = mockCustomer1();
            CustomerEntity newCustomerEntity = mockCustomerToEntity(mockCustomer1());

            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(newCustomerEntity);

            Customer customerAdded = customerDAO.addCustomer(newCustomer);

            verify(customerRepository).save(any(CustomerEntity.class));
            assertEquals(newCustomer, customerAdded);
        }

        @Test
        void deleteCustomer_deletes_customer_from_repository() {
            CustomerEntity customerEntityToDelete = mockCustomerEntity1();
            String loyaltyId = customerEntityToDelete.getLoyaltyId();
            Customer expectedCustomer = mockEntityToCustomer(customerEntityToDelete);

            when(customerRepository.findByLoyaltyId(loyaltyId)).thenReturn(customerEntityToDelete);

            Customer deletedCustomer = customerDAO.deleteCustomer(loyaltyId);

            verify(customerRepository).findByLoyaltyId(loyaltyId);
            verify(customerRepository).delete(customerEntityToDelete);
            assertEquals(expectedCustomer, deletedCustomer);
        }

        @Test
        void setCustomerToUpdatedCustomer_updates_customer_fields() {
            CustomerEntity customerEntity = mockCustomerEntity1();
            Customer updatedCustomer = mockCustomer1UpdateTo3();

            CustomerEntity updatedEntity = customerDAO.setCustomerToUpdatedCustomer(customerEntity, updatedCustomer);

            assertEquals(updatedCustomer.getFirstName(), updatedEntity.getFirstName());
            assertEquals(updatedCustomer.getLastName(), updatedEntity.getLastName());
            assertEquals(updatedCustomer.getPhoneNumber(), updatedEntity.getPhoneNumber());
            assertEquals(updatedCustomer.getAddress(), updatedEntity.getAddress());
            assertEquals(updatedCustomer.getBalance(), updatedEntity.getBalance());
            assertEquals(updatedCustomer.getPets().size(), updatedEntity.getPets().size());
        }

        @Test
        void update_returns_updated_customer_object() {
            Customer mockCustomerUpdate = mockCustomer1UpdateTo3();
            String loyaltyId = mockCustomerUpdate.getLoyaltyId();
            CustomerEntity mockOriginalCustomerEntity1 = mockCustomerEntity1();

            when(customerRepository.findByLoyaltyId(loyaltyId)).thenReturn(mockOriginalCustomerEntity1);
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(mockCustomerToEntity(mockCustomerUpdate));

            Customer actualUpdatedCustomer = customerDAO.update(mockCustomerUpdate);

            verify(customerRepository).findByLoyaltyId(loyaltyId);
            verify(customerRepository).delete(mockOriginalCustomerEntity1);
            verify(customerRepository).save(any(CustomerEntity.class));
            assertEquals(mockCustomerUpdate, actualUpdatedCustomer);
        }
    }

    public static Customer mockEntityToCustomer(CustomerEntity entity) {
        if (entity == null) return null;

        List<Pet> pets = entity.getPets() == null ? List.of() :
                entity.getPets().stream()
                        .map(petEntity -> Pet.builder()
                                .petType(petEntity.getPetType())
                                .petName(petEntity.getPetName())
                                .build())
                        .toList();

        return Customer.builder()
                .loyaltyId(entity.getLoyaltyId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .pets(pets)
                .balance(entity.getBalance())
                .build();
    }

    public static CustomerEntity mockCustomerToEntity(Customer customer) {
        if (customer == null) return null;

        List<PetEntity> petEntities = customer.getPets() == null ? List.of() :
                customer.getPets().stream()
                        .map(pet -> PetEntity.builder()
                                .petType(pet.getPetType())
                                .petName(pet.getPetName())
                                .build())
                        .toList();

        return CustomerEntity.builder()
                .loyaltyId(customer.getLoyaltyId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .pets(petEntities)
                .balance(customer.getBalance())
                .build();
    }

    public static List<Customer> mockEntityListToCustomerList(List<CustomerEntity> entityList) {
        if (entityList == null) return List.of();

        return entityList.stream()
                .map(entity -> {
                    List<Pet> pets = entity.getPets() == null ? List.of() :
                            entity.getPets().stream()
                                    .map(petEntity -> Pet.builder()
                                            .petType(petEntity.getPetType())
                                            .petName(petEntity.getPetName())
                                            .build())
                                    .toList();

                    return Customer.builder()
                            .loyaltyId(entity.getLoyaltyId())
                            .firstName(entity.getFirstName())
                            .lastName(entity.getLastName())
                            .phoneNumber(entity.getPhoneNumber())
                            .address(entity.getAddress())
                            .pets(pets)
                            .balance(entity.getBalance())
                            .build();
                })
                .toList();
    }
}
