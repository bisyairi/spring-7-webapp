package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Customer;
import guru.springframework.spring7webapp.services.CustomerService;
import guru.springframework.spring7webapp.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getAllCustomers() throws Exception {

        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3))
                );
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().getLast();

        given(customerService.getCustomerById(customerTest.getId())).willReturn(customerTest);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, + customerTest.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerTest.getId()))
                .andExpect(jsonPath("$.customerName", is(customerTest.getCustomerName())));
    }

    @Test
    void createCustomer() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().getFirst();
        customerTest.setId(null);
        customerTest.setVersion(null);

        given(customerService.createNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.getAllCustomers().getFirst());

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customerTest))
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCustomer() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().getFirst();

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customerTest))
                ).andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(any(Integer.class), any(Customer.class));
    }

    @Test
    void deleteCustomer() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().getFirst();

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(eq(customerTest.getId()));
    }

    @Test
    void patchCustomer() throws Exception {
        Customer customerTest = customerServiceImpl.getAllCustomers().getFirst();

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Customer Name");

        mockMvc.perform(patch( CustomerController.CUSTOMER_PATH_ID, customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customerMap))
                ).andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(
                integerArgumentCaptor.capture(),
                customerArgumentCaptor.capture());

        assertThat(integerArgumentCaptor.getValue()).isEqualTo(customerTest.getId());
        assertThat(customerArgumentCaptor.getValue().getCustomerName()).isEqualTo(customerMap.get("customerName"));
    }
}