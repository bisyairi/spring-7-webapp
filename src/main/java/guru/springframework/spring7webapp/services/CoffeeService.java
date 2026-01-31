package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CoffeeDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoffeeService {
    List<CoffeeDTO> getAllCoffees();

    Optional<CoffeeDTO> getCoffeeById(UUID id);

    CoffeeDTO saveNewCoffee(CoffeeDTO coffee);

    Optional<CoffeeDTO> updateCoffeeById(UUID id, CoffeeDTO coffee);

    Boolean deleteCoffeeById(UUID coffeeId);

    void patchCoffeeById(UUID coffeeId, CoffeeDTO coffeeUpdates);
}
