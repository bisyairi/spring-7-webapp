package guru.springframework.spring7webapp.controllers.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"MY", "DEV"})
@SpringBootTest
class MYi18NControllerTest {

    @Autowired
    Myi18nController myi18nController;

    @Test
    void index() {
        System.out.println(myi18nController.index());
    }
}