package guru.springframework.spring7webapp.bootstrap;

import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import guru.springframework.spring7webapp.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(coffeeRepository, customerRepository);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);

        assertThat(coffeeRepository.count()).isGreaterThan(0);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}