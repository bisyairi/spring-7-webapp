package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.entities.Customer;
import guru.springframework.spring7webapp.model.CustomerDTO;
import guru.springframework.spring7webapp.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void getAllCustomers() {
        List<CustomerDTO> customerDTO = customerController.getAllCustomers();

        assertThat(customerDTO).size().isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void customerNotFound() {
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTO = customerController.getAllCustomers();

        assertEquals(0, customerDTO.size());
    }

    @Test
    void getCustomerById() {

        Customer customer = customerRepository.findAll().getFirst();

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertNotNull(customerController.getCustomerById(customer.getId()));
    }

    @Test
    void customerIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(4));
    }
}