package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Coffee;
import guru.springframework.spring7webapp.services.CoffeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CoffeeController {

    public static final String COFFEE_BASE = "/api/v1/coffee";
    public static final String COFFEE_BASE_ID = COFFEE_BASE + "/{coffeeId}";

    private final CoffeeService coffeeService;

    @GetMapping(value = COFFEE_BASE)
    public List<Coffee> getAllCoffees(){
        return coffeeService.getAllCoffees();
    }

    @GetMapping(value = COFFEE_BASE_ID)
    public Coffee getCoffeeById(@PathVariable("coffeeId") UUID coffeeId){

        log.debug("Getting coffee by id: {}", coffeeId.toString());

        return coffeeService.getCoffeeById(coffeeId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(COFFEE_BASE)
    public ResponseEntity<Void> createCoffee(@RequestBody Coffee coffee){
        Coffee savedCoffee = coffeeService.saveNewCoffee(coffee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", COFFEE_BASE + "/" + savedCoffee.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> updateCoffee(@PathVariable("coffeeId")UUID coffeeId, @RequestBody Coffee coffee){

        coffeeService.updateCoffeeById(coffeeId, coffee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> removeCoffee(@PathVariable("coffeeId") UUID coffeeId){
        coffeeService.deleteCoffeeById(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(COFFEE_BASE_ID)
    public ResponseEntity<Void> patchCoffee(@PathVariable("coffeeId") UUID coffeeId, @RequestBody Coffee coffee){
        coffeeService.patchCoffeeById(coffeeId, coffee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
