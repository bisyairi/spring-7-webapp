package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.mappers.CoffeeMapper;
import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CoffeeServiceImplJPA implements CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final CoffeeMapper coffeeMapper;

    @Override
    public List<CoffeeDTO> getAllCoffees() {
        return coffeeRepository.findAll()
                .stream()
                .map(coffeeMapper::coffeeToCoffeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CoffeeDTO> getCoffeeById(UUID id) {
        return Optional.ofNullable(coffeeMapper.coffeeToCoffeeDto(coffeeRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public CoffeeDTO saveNewCoffee(CoffeeDTO coffee) {
        return coffeeMapper.coffeeToCoffeeDto(coffeeRepository.save(coffeeMapper.coffeeDtoToCoffee(coffee)));
    }

    @Override
    public Optional<CoffeeDTO> updateCoffeeById(UUID id, CoffeeDTO coffee) {

        AtomicReference<Optional<CoffeeDTO>> atomicReference = new AtomicReference<>();

        coffeeRepository.findById(id).ifPresentOrElse(foundCoffee -> {
            foundCoffee.setCoffeeName(coffee.getCoffeeName());
            foundCoffee.setCoffeeStyle(coffee.getCoffeeStyle());
            atomicReference.set(Optional.of(coffeeMapper.coffeeToCoffeeDto(coffeeRepository.save(foundCoffee))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCoffeeById(UUID coffeeId) {
        if (coffeeRepository.existsById(coffeeId)) {
            coffeeRepository.deleteById(coffeeId);
            return true;
        }

        return false;
    }

    @Override
    public void patchCoffeeById(UUID coffeeId, CoffeeDTO coffeeUpdates) {

    }
}
