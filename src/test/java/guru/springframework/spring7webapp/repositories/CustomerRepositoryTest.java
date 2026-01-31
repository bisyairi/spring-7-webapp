package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());

        assertThat(customer).isNotNull();
        assertThat(customer.getCustomerName()).isNotNull();
        assertThat(customer.getCustomerName()).isEqualTo("Test Customer");
    }
}