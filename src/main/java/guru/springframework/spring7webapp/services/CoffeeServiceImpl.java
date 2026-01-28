package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Coffee;
import guru.springframework.spring7webapp.model.CoffeeStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CoffeeServiceImpl implements CoffeeService {

    private final Map<UUID, Coffee> coffeeMap;

    public CoffeeServiceImpl() {
        this.coffeeMap = new HashMap<>();

        Coffee coffee1 = Coffee.builder().
                id(UUID.randomUUID()).
                version(1).
                coffeeName("Spanish Latte").
                coffeeStyle(CoffeeStyle.LATTE).
                upc("123456").
                quantityOnHand(101).
                price(new BigDecimal("10.99")).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Coffee coffee2 = Coffee.builder().
                id(UUID.randomUUID()).
                version(1).
                coffeeName("Mocha Frappe").
                coffeeStyle(CoffeeStyle.MOCHA).
                upc("123333").
                quantityOnHand(220).
                price(new BigDecimal("12.50")).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Coffee coffee3 = Coffee.builder().
                id(UUID.randomUUID()).
                version(1).
                coffeeName("Zero Latte").
                coffeeStyle(CoffeeStyle.LATTE).
                upc("123423").
                quantityOnHand(300).
                price(new BigDecimal("11.99")).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        coffeeMap.put(coffee1.getId(), coffee1);
        coffeeMap.put(coffee2.getId(), coffee2);
        coffeeMap.put(coffee3.getId(), coffee3);
    }

    @Override
    public List<Coffee> getAllCoffees(){
        return new ArrayList<>(coffeeMap.values());
    }

    @Override
    public Coffee getCoffeeById(UUID id) {

        log.debug("Getting coffee by id: {}", id.toString());

        return coffeeMap.get(id);
    }

    @Override
    public Coffee saveNewCoffee(Coffee coffee) {
        Coffee savedCoffee = Coffee.builder()
                .id(UUID.randomUUID())
                .version(coffee.getVersion())
                .coffeeName(coffee.getCoffeeName())
                .coffeeStyle(coffee.getCoffeeStyle())
                .upc(coffee.getUpc())
                .quantityOnHand(coffee.getQuantityOnHand())
                .price(coffee.getPrice())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        coffeeMap.put(savedCoffee.getId(), savedCoffee);
        return savedCoffee;
    }

    @Override
    public void updateCoffeeById(UUID coffeeId, Coffee coffee) {
        Coffee existingCoffee = coffeeMap.get(coffeeId);

        if (existingCoffee == null) {
            throw new IllegalArgumentException("Coffee not found with id: " + coffeeId);
        }

        existingCoffee.setCoffeeName(coffee.getCoffeeName());
        existingCoffee.setUpc(coffee.getUpc());
        existingCoffee.setQuantityOnHand(coffee.getQuantityOnHand());
        existingCoffee.setPrice(coffee.getPrice());
        existingCoffee.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void deleteCoffeeById(UUID coffeeId) {
        coffeeMap.remove(coffeeId);
    }

    @Override
    public void patchCoffeeById(UUID coffeeId, Coffee coffee) {
        Coffee existingCoffee = coffeeMap.get(coffeeId);

        if (StringUtils.hasText(coffee.getCoffeeName())) {
            existingCoffee.setCoffeeName(coffee.getCoffeeName());
        }

        if (coffee.getVersion() != null) {
            existingCoffee.setVersion(coffee.getVersion());
        }
    }
}
