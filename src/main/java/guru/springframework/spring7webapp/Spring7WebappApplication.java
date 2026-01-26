package guru.springframework.spring7webapp;

import guru.springframework.spring7webapp.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring7WebappApplication {

    static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Spring7WebappApplication.class, args);

        MyController controller = ctx.getBean(MyController.class);

        System.out.println("main method");
        System.out.println(controller.index());
    }

}
