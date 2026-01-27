package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Coffee;
import guru.springframework.spring7webapp.services.CoffeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    public Coffee getCoffeeById(UUID id){

        log.debug("Getting coffee by id: {}", id.toString());

        return coffeeService.getCoffeeById(id);
    }
}
