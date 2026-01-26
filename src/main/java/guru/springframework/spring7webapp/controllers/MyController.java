package guru.springframework.spring7webapp.controllers;

import guru.springframework.spring7webapp.services.GreetingService;
import guru.springframework.spring7webapp.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private final GreetingService greetingService;

    public MyController() {
        this.greetingService = new GreetingServiceImpl();
    }

    public String index() {
        System.out.println("dalam controller method index");

        return "index1";
    }
}
