package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Coffee;

import java.util.UUID;

public interface CoffeeService {
    Coffee getCoffeeById(UUID id);
}
