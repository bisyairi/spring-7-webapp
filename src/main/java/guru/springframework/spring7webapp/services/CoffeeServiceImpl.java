package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Coffee;
import guru.springframework.spring7webapp.model.CoffeeStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Override
    public Coffee getCoffeeById(UUID id) {

        log.debug("Getting coffee by id: {}", id.toString());

        return Coffee.builder().
                id(id).
                version(1).
                coffeeName("Spanish Latte").
                coffeeStyle(CoffeeStyle.LATTE).
                upc("123456").
                quantityOnHand(300).
                price(new BigDecimal("10.99")).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();
    }
}
