package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.CoffeeDTO;
import guru.springframework.spring7webapp.services.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CoffeeController {

    public static final String COFFEE_BASE = "/api/v1/coffee";
    public static final String COFFEE_BASE_ID = COFFEE_BASE + "/{coffeeId}";

    private final CoffeeService coffeeService;

    @GetMapping(value = COFFEE_BASE)
    public List<CoffeeDTO> getAllCoffees(){
        return coffeeService.getAllCoffees();
    }

    @GetMapping(value = COFFEE_BASE_ID)
    public CoffeeDTO getCoffeeById(@PathVariable("coffeeId") UUID coffeeId){

        log.debug("Getting coffee by id: {}", coffeeId.toString());

        return coffeeService.getCoffeeById(coffeeId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(COFFEE_BASE)
    public ResponseEntity<Void> createCoffee(@Validated @RequestBody CoffeeDTO coffee){
        CoffeeDTO savedCoffee = coffeeService.saveNewCoffee(coffee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", COFFEE_BASE + "/" + savedCoffee.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> updateCoffee(@PathVariable("coffeeId")UUID coffeeId, @Validated @RequestBody CoffeeDTO coffee){

        if (coffeeService.updateCoffeeById(coffeeId, coffee).isEmpty()){
            throw new NotFoundException();
        };

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> removeCoffee(@PathVariable("coffeeId") UUID coffeeId){
        if (!coffeeService.deleteCoffeeById(coffeeId)){
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> patchCoffee(@PathVariable("coffeeId") UUID coffeeId,@Validated @RequestBody CoffeeDTO coffee){
        coffeeService.patchCoffeeById(coffeeId, coffee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
