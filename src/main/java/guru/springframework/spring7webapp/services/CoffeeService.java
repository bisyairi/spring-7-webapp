package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CoffeeDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoffeeService {
    Page<CoffeeDTO> getAllCoffees(String name, Integer pageNumber, Integer pageSize);

    Optional<CoffeeDTO> getCoffeeById(UUID id);

    CoffeeDTO saveNewCoffee(CoffeeDTO coffee);

    Optional<CoffeeDTO> updateCoffeeById(UUID id, CoffeeDTO coffee);

    Boolean deleteCoffeeById(UUID coffeeId);

    void patchCoffeeById(UUID coffeeId, CoffeeDTO coffeeUpdates);
}
