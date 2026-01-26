package guru.springframework.spring7webapp.controllers;

import org.junit.jupiter.api.Test;

class MyControllerTest {

    @Test
    void index() {
        MyController controller = new MyController();

        System.out.println(controller.index());
    }
}