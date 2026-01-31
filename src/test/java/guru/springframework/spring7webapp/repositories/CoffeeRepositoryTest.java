package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Coffee;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CoffeeRepositoryTest {

    @Autowired
    CoffeeRepository coffeeRepository;

    @Test
    void testSaveCoffee() {
        Coffee coffee = coffeeRepository.save(Coffee.builder()
                .coffeeName("Espresso")
                .build());

        coffeeRepository.flush();

        assertThat(coffee).isNotNull();
        assertThat(coffee.getId()).isNotNull();
    }

    @Test
    void testSaveCoffeNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            coffeeRepository.save(Coffee.builder()
                    .coffeeName("A very very very long coffee name that is more than 50 characters")
                    .build());

            coffeeRepository.flush();
        });
    }
}