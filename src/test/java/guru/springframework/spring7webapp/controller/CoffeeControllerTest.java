package guru.springframework.spring7webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class CoffeeControllerTest {

    @Autowired
    CoffeeController coffeeController;

    @Test
    void getCoffeeById() {
        System.out.println(coffeeController.getCoffeeById(UUID.randomUUID()));
    }
}