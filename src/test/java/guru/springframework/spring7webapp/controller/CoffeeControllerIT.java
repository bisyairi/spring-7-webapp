package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.entities.Coffee;
import guru.springframework.spring7webapp.mappers.CoffeeMapper;
import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CoffeeControllerIT {

    @Autowired
    CoffeeController coffeeController;

    @Autowired
    CoffeeRepository coffeeRepository;

    @Autowired
    CoffeeMapper coffeeMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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

    @Test
    void testPatchBadCoffeeName() throws Exception {
        Coffee coffee = coffeeRepository.findAll().getFirst();

        Map<String, Object> coffeeMap = new HashMap<>();
        coffeeMap.put("coffeeName", "A very very very long coffee name that is more than 50 characters");

        mockMvc.perform(patch(CoffeeController.COFFEE_BASE_ID, coffee.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeMap))
                ).andExpect(status().isBadRequest());
    }
}