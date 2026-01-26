package guru.springframework.spring7webapp.controllers.environment;

import guru.springframework.spring7webapp.controllers.EnvironmentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("UAT")
@SpringBootTest
class EnvironmentControllerUATTest {

    @Autowired
    EnvironmentController environmentController;

    @Test
    void index() {
        System.out.println(environmentController.index());
    }
}