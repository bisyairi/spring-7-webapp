package guru.springframework.spring7webapp;

import guru.springframework.spring7webapp.controllers.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Spring7WebappApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyController controller;

    @Test
    void testAutowiredOfController() {
        System.out.println(controller.index());
    }

    @Test
    void testGetControllerFromContext() {
        MyController controller = applicationContext.getBean(MyController.class);
        System.out.println(controller.index());
    }

    @Test
    void contextLoads() {
    }

}
