package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.entities.Coffee;
import guru.springframework.spring7webapp.mappers.CoffeeMapper;
import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.repositories.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    @Override
    public Page<CoffeeDTO> getAllCoffees(String name, Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        Page<Coffee> coffees;

        if (StringUtils.hasText(name)) {
            coffees = listCoffeesByName(name, pageRequest);
        } else {
            coffees = coffeeRepository.findAll(pageRequest);
        }

        return coffees.map(coffeeMapper::coffeeToCoffeeDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                pageSize = 1000;
            } else {
                pageSize = pageSize;
            }
            queryPageSize = pageSize;
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "coffeeName");

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }

    private Page<Coffee> listCoffeesByName(String name, Pageable pageable) {
        return coffeeRepository.findAllByCoffeeNameIsLikeIgnoreCase("%"+ name + "%", pageable);
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
