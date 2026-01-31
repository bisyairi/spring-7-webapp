package guru.springframework.spring7webapp.bootstrap;

import guru.springframework.spring7webapp.entities.Coffee;
import guru.springframework.spring7webapp.entities.Customer;
import guru.springframework.spring7webapp.model.CoffeeStyle;
import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import guru.springframework.spring7webapp.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {
    private final CoffeeRepository coffeeRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCoffeeData();
        loadCustomerData();
    }

    private void loadCoffeeData() {
        if (coffeeRepository.count() == 0) {
            Coffee coffee1 = Coffee.builder().
                    coffeeName("Spanish Latte").
                    coffeeStyle(CoffeeStyle.LATTE).
                    upc("123456").
                    quantityOnHand(101).
                    price(new BigDecimal("10.99")).
                    createdAt(LocalDateTime.now()).
                    updatedAt(LocalDateTime.now()).
                    build();

            Coffee coffee2 = Coffee.builder().
                    coffeeName("Mocha Frappe").
                    coffeeStyle(CoffeeStyle.MOCHA).
                    upc("123333").
                    quantityOnHand(220).
                    price(new BigDecimal("12.50")).
                    createdAt(LocalDateTime.now()).
                    updatedAt(LocalDateTime.now()).
                    build();

            Coffee coffee3 = Coffee.builder().
                    coffeeName("Zero Latte").
                    coffeeStyle(CoffeeStyle.LATTE).
                    upc("123423").
                    quantityOnHand(300).
                    price(new BigDecimal("11.99")).
                    createdAt(LocalDateTime.now()).
                    updatedAt(LocalDateTime.now()).
                    build();

            coffeeRepository.save(coffee1);
            coffeeRepository.save(coffee2);
            coffeeRepository.save(coffee3);
        }
    }

    private void loadCustomerData() {

        Customer customer1 = Customer.builder().
                customerName("Iwan").
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Customer customer2 = Customer.builder().
                customerName("Afiq").
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Customer customer3 = Customer.builder().
                customerName("Atan").
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
    }


}
