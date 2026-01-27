package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Coffee;
import guru.springframework.spring7webapp.services.CoffeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/coffee")
@RestController
public class CoffeeController {

    private final CoffeeService coffeeService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Coffee> getAllCoffees(){
        return coffeeService.getAllCoffees();
    }

    @RequestMapping(value = "{coffeeId}", method = RequestMethod.GET)
    public Coffee getCoffeeById(@PathVariable("coffeeId") UUID coffeeId){

        log.debug("Getting coffee by id: {}", coffeeId.toString());

        return coffeeService.getCoffeeById(coffeeId);
    }

    @PostMapping
    public ResponseEntity createCoffee(@RequestBody Coffee coffee){
        Coffee savedCoffee = coffeeService.saveNewCoffee(coffee);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/coffee/" + savedCoffee.getId());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("{coffeeId}")
    public ResponseEntity updateCoffee(@PathVariable("coffeeId")UUID coffeeId, @RequestBody Coffee coffee){

        coffeeService.updateCoffeeById(coffeeId, coffee);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{coffeeId}")
    public ResponseEntity removeCoffee(@PathVariable("coffeeId") UUID coffeeId){
        coffeeService.deleteCoffeeById(coffeeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
