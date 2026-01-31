package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.entities.Coffee;
import guru.springframework.spring7webapp.mappers.CoffeeMapper;
import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoffeeControllerIT {

    @Autowired
    CoffeeController coffeeController;

    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    CoffeeMapper coffeeMapper;

    @Test
    void getAllCoffees() {
        List<CoffeeDTO> coffeeDTOS = coffeeController.getAllCoffees();

        assertThat(coffeeDTOS).size().isEqualTo(3);
    }

    @Transactional
    @Rollback
    @Test
    void testEmptyList() {
        coffeeRepository.deleteAll();
        List<CoffeeDTO> coffeeDTOS = coffeeController.getAllCoffees();

        assertThat(coffeeDTOS).size().isEqualTo(0);
    }

    @Test
    void testGetCoffeeById() {
        Coffee coffee =  coffeeRepository.findAll().getFirst();

        CoffeeDTO coffeeDTO = coffeeController.getCoffeeById(coffee.getId());

        assertThat(coffeeDTO).isNotNull();
    }

    @Test
    void testCoffeeIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> coffeeController.getCoffeeById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testCreateCoffee() {
        CoffeeDTO coffeeDTO = CoffeeDTO.builder().coffeeName("Cappuccino").build();

        ResponseEntity<Void> responseEntity = coffeeController.createCoffee(coffeeDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Coffee savedCoffee = coffeeRepository.findById(savedUUID).orElse(null);
        assertThat(savedCoffee).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateCoffeById() {
        Coffee coffee = coffeeRepository.findAll().getFirst();

        CoffeeDTO coffeeDTO = coffeeMapper.coffeeToCoffeeDto(coffee);

        coffeeDTO.setId(null);
        coffeeDTO.setVersion(null);
        final String coffeeName = "Kopi Kapal Api";
        coffeeDTO.setCoffeeName(coffeeName);

        ResponseEntity<Void> responseEntity = coffeeController.updateCoffee(coffee.getId(), coffeeDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Coffee updatedCoffee = coffeeRepository.findById(coffee.getId()).get();
        assertThat(updatedCoffee.getCoffeeName()).isEqualTo(coffeeName);
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class,
                () -> coffeeController.updateCoffee(UUID.randomUUID(), CoffeeDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteById() {
        Coffee coffee = coffeeRepository.findAll().getFirst();

        ResponseEntity<Void> responseEntity = coffeeController.removeCoffee(coffee.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(coffeeRepository.findById(coffee.getId())).isEmpty();
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(NotFoundException.class,
                () -> coffeeController.removeCoffee(UUID.randomUUID()));
    }
}