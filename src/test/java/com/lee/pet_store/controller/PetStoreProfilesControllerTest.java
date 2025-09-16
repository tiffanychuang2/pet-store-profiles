package com.lee.pet_store.controller;

import com.lee.pet_store.model.Customer;
import com.lee.pet_store.service.AccountManagementService;
import com.lee.pet_store.testUtil.MockUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.lee.pet_store.testUtil.MockUtils.mockCustomer1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {PetStoreProfilesController.class, AccountManagementService.class})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PetStoreProfilesControllerTest {

    @InjectMocks
    private PetStoreProfilesController petStoreProfilesController;

    @Mock
    AccountManagementService accountManagementService;

    @Nested
    class happy_path {

        @Test
        void testPing() {
            final ResponseEntity<String> response = petStoreProfilesController.ping();
            Assertions.assertNotNull(response.getBody());
            assertTrue(response.getBody().contains("status: Healthy"));
        }

        @Test
        void getAllCustomers_happy_path() {
            List<Customer> customers = new ArrayList<>(
                    Arrays.asList(
                            MockUtils.mockCustomer1(),
                            MockUtils.mockCustomer2()
                    )
            );
            ResponseEntity<List<Customer>> expectedResponse = new ResponseEntity<>(customers, HttpStatus.OK);

            when(accountManagementService.getAllCustomers()).thenReturn(customers);

            ResponseEntity<List<Customer>> actualResponse = petStoreProfilesController.getAllCustomers();

            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void getCustomerByLoyaltyId_happy_path() {
            Customer mockCustomer = mockCustomer1();
            String loyaltyId = mockCustomer.getLoyaltyId();
            ResponseEntity<Optional<Customer>> expectedResponse = new ResponseEntity<>(Optional.of(mockCustomer), HttpStatus.OK);

            when(accountManagementService.getCustomerByLoyaltyId(loyaltyId)).thenReturn(Optional.of(mockCustomer));

            ResponseEntity<Optional<Customer>> actualResponse =  petStoreProfilesController.getCustomerByLoyaltyId(loyaltyId);

            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void addCustomer_happy_path() {
            Customer mockCustomer1 = mockCustomer1();
            ResponseEntity<Customer> expectedResponse = new ResponseEntity<>(mockCustomer1, HttpStatus.CREATED);

            when(accountManagementService.addCustomer(mockCustomer1)).thenReturn(mockCustomer1);
            ResponseEntity<Customer> actualResponse = petStoreProfilesController.addCustomer(mockCustomer1);

            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void deleteCustomer_happy_path() {
            List<Customer> customers = new ArrayList<>(
                    Arrays.asList(
                            mockCustomer1(),
                            MockUtils.mockCustomer2()
                    )
            );
            ResponseEntity<Customer> expectedResponse = new ResponseEntity<>(HttpStatus.ACCEPTED);

            when(accountManagementService.deleteCustomerByLoyaltyId(mockCustomer1().getLoyaltyId())).thenReturn(mockCustomer1());

            ResponseEntity<Customer> actualResposne = petStoreProfilesController.deleteCustomer(mockCustomer1().getLoyaltyId());

            assertEquals(expectedResponse, actualResposne);
        }

        @Test
        void updateCustomer_happy_path() {
            String mock1LoyaltyId = MockUtils.mockCustomer1().getLoyaltyId();
            Customer updatedCustomer = MockUtils.mockCustomer3Update();
            Customer actualCustomer = MockUtils.mockCustomer1();

            when(accountManagementService.updateCustomer(updatedCustomer)).thenReturn(actualCustomer);

            ResponseEntity<Customer> actualResponse = petStoreProfilesController.updateCustomer(updatedCustomer);

            assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        }
    }
}
