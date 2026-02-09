package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.config.SpringSecConfig;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoffeeController.class)
@ExtendWith(MockitoExtension.class)
@Import(SpringSecConfig.class)
class CoffeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    UserDetailsService userDetailsService;

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

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor = jwt().jwt(jwt -> {
        jwt.claims(claims -> {
                    claims.put("scope", "message-read");
                    claims.put("scope", "message-write");
                })
                .subject("messaging-client")
                .notBefore(Instant.now().minusSeconds(5L));
    });

    @BeforeEach
    void setUpSecurity() {
        UserDetails user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER").build();

        given(userDetailsService.loadUserByUsername("user")).willReturn(user);
    }

    @Test
    void getAllCoffees() throws Exception {

        given(coffeeService.getAllCoffees(any(), any(), any()))
                .willReturn(coffeeServiceImpl.getAllCoffees(null, null, null));

        mockMvc.perform(get(CoffeeController.COFFEE_BASE)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    void getCoffeeById() throws Exception {

        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();

        given(coffeeService.getCoffeeById(coffeeTest.getId())).willReturn(Optional.of(coffeeTest));

        mockMvc.perform(get(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(coffeeTest.getId().toString())))
                .andExpect(jsonPath("$.coffeeName").value(coffeeTest.getCoffeeName()));
    }

    @Test
    void createCoffee() throws Exception {

        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();
        coffeeTest.setId(UUID.randomUUID());
        coffeeTest.setVersion(null);

        given(coffeeService.saveNewCoffee(any(CoffeeDTO.class)))
                .willReturn(coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst());

        mockMvc.perform(post(CoffeeController.COFFEE_BASE)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeTest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();

        given(coffeeService.updateCoffeeById(any(), any())).willReturn(Optional.of(coffeeTest));

        mockMvc.perform(put(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeTest)))
                .andExpect(status().isNoContent());

        verify(coffeeService).updateCoffeeById(any(UUID.class), any(CoffeeDTO.class));
    }

    @Test
    void deleteCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();

        given(coffeeService.deleteCoffeeById(any(UUID.class))).willReturn(true);

        mockMvc.perform(delete(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                .with(jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(coffeeService).deleteCoffeeById(uuidArgumentCaptor.capture());

        assertThat(coffeeTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void patchCoffee() throws Exception {
        CoffeeDTO coffeeTest = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();

        Map<String, Object> coffeeMap = new HashMap<>();
        coffeeMap.put("coffeeName", "New Name");

        mockMvc.perform(patch(CoffeeController.COFFEE_BASE_ID, coffeeTest.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeMap)))
                .andExpect(status().isNoContent());

        verify(coffeeService).patchCoffeeById(uuidArgumentCaptor.capture(), coffeeArgumentCaptor.capture());

        assertThat(coffeeTest.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(coffeeMap.get("coffeeName")).isEqualTo(coffeeArgumentCaptor.getValue().getCoffeeName());
    }

    @Test
    void getCoffeeByIdNotFound() throws Exception {

        given(coffeeService.getCoffeeById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CoffeeController.COFFEE_BASE_ID, UUID.randomUUID())
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCoffeeNullName() throws Exception {
        CoffeeDTO coffeeDTO = CoffeeDTO.builder()
                .coffeeName(null)
                .build();

        given(coffeeService.saveNewCoffee(any(CoffeeDTO.class)))
                .willReturn(coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst());

        MvcResult mvcResult = mockMvc.perform(post(CoffeeController.COFFEE_BASE)
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coffeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(2)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateCoffeeBlankName() throws Exception {
        CoffeeDTO coffeeDTO = coffeeServiceImpl.getAllCoffees(null, 1, 25).getContent().getFirst();
        coffeeDTO.setCoffeeName("");

        given(coffeeService.updateCoffeeById(any(), any())).willReturn(Optional.of(coffeeDTO));

        MvcResult mvcResult = mockMvc.perform(put(CoffeeController.COFFEE_BASE_ID, coffeeDTO.getId())
                        .with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(coffeeDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}