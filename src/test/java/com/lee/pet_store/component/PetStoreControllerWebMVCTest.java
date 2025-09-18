package com.lee.pet_store.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.pet_store.model.Customer;
import com.lee.pet_store.service.AccountManagementService;
import com.lee.pet_store.testUtil.MockUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebAppConfiguration
public class PetStoreControllerWebMVCTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private AccountManagementService accountManagementService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    class HappyPath {

        @Test
        void ping_returns_healthy_status() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/ping"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(("status: Healthy")));
        }

        @Test
        void when_updateCustomer_is_called_customer_is_updated() throws Exception {
            Customer customerUpdate = MockUtils.mockCustomer1UpdateTo3();

            when(accountManagementService.updateCustomer(customerUpdate)).thenReturn(customerUpdate);

            String customerJson = objectMapper.writeValueAsString(customerUpdate);

            mockMvc.perform(MockMvcRequestBuilders.put("/update-customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(customerJson))
                    .andExpect(status().isOk())
                    .andExpect(content().json(customerJson));
        }
    }

}
