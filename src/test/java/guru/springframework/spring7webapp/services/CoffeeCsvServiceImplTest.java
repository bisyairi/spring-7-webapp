package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CoffeeCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoffeeCsvServiceImplTest {

    CoffeeCsvService coffeeCsvService = new CoffeeCsvServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {

        File file = ResourceUtils.getFile("classpath:csvdata/coffees.csv");

        List<CoffeeCSVRecord> records = coffeeCsvService.convertCsv(file);

        System.out.println(records.size());

        assertNotEquals(0, records.size());
    }
}