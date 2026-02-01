package guru.springframework.spring7webapp.bootstrap;

import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import guru.springframework.spring7webapp.repositories.CustomerRepository;
import guru.springframework.spring7webapp.services.CoffeeCsvService;
import guru.springframework.spring7webapp.services.CoffeeCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(CoffeeCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CoffeeCsvService coffeeCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(coffeeRepository, customerRepository, coffeeCsvService);
    }

    @Test
    void testRun() throws Exception {
        bootstrapData.run(null);

        assertThat(coffeeRepository.count()).isGreaterThan(0);
//        assertThat(customerRepository.count()).isEqualTo(3);
    }
}