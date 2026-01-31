package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CoffeeRepositoryTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Test
    void testSaveCoffee() {
        Coffee coffee = coffeeRepository.save(Coffee.builder()
                .coffeeName("Espresso")
                .build());

        assertThat(coffee).isNotNull();
        assertThat(coffee.getId()).isNotNull();
    }
}