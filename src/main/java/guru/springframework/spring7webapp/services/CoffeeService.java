package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Coffee;

import java.util.List;
import java.util.UUID;

public interface CoffeeService {
    List<Coffee> getAllCoffees();

    Coffee getCoffeeById(UUID id);

    Coffee saveNewCoffee(Coffee coffee);

    void updateCoffeeById(UUID id, Coffee coffee);

    void deleteCoffeeById(UUID coffeeId);
}
