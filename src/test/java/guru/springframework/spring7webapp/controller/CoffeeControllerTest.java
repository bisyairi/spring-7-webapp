package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.services.CoffeeService;
import guru.springframework.spring7webapp.services.CoffeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoffeeController.class)
@ExtendWith(MockitoExtension.class)
class CoffeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CoffeeDTO> coffeeArgumentCaptor;

    @MockitoBean
    CoffeeService coffeeService;

    CoffeeServiceImpl coffeeServiceImpl;

    @BeforeEach
    void setUp() {
        coffeeServiceImpl = new CoffeeServiceImpl();
    }

    @Test
    void getAllCoffees() throws Exception {

        given(coffeeService.getAllCoffees()).willReturn(coffeeServiceImpl.getAllCoffees());

        mockMvc.perform(get(CoffeeController.COFFEE_BASE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCoffeeById() throws Exception {

        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees().getFirst();

        given(coffeeService.getCoffeeById(coffeeTest.getId())).willReturn(Optional.of(coffeeTest));

        mockMvc.perform(get(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(coffeeTest.getId().toString())))
                .andExpect(jsonPath("$.coffeeName").value(coffeeTest.getCoffeeName()));
    }

    @Test
    void createCoffee() throws Exception {

        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees().getFirst();
        coffeeTest.setId(UUID.randomUUID());
        coffeeTest.setVersion(null);

        given(coffeeService.saveNewCoffee(any(CoffeeDTO.class))).willReturn(coffeeServiceImpl.getAllCoffees().getFirst());

        mockMvc.perform(post(CoffeeController.COFFEE_BASE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeTest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees().getFirst();

        given(coffeeService.updateCoffeeById(any(), any())).willReturn(Optional.of(coffeeTest));

        mockMvc.perform(put(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeTest)))
                .andExpect(status().isNoContent());

        verify(coffeeService).updateCoffeeById(any(UUID.class), any(CoffeeDTO.class));
    }

    @Test
    void deleteCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees().getFirst();

        given(coffeeService.deleteCoffeeById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(coffeeService).deleteCoffeeById(uuidArgumentCaptor.capture());

        assertThat(coffeeTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void patchCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees().get(0);

        mockMvc.perform(patch(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeTest)))
                .andExpect(status().isNoContent());

        verify(coffeeService).patchCoffeeById(uuidArgumentCaptor.capture(), coffeeArgumentCaptor.capture());

        assertThat(coffeeTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(coffeeArgumentCaptor.getValue().getCoffeeName()).isEqualTo(coffeeTest.getCoffeeName());
    }

    @Test
    void getCoffeeByIdNotFound() throws Exception {

        given(coffeeService.getCoffeeById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CoffeeController.COFFEE_BASE_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}