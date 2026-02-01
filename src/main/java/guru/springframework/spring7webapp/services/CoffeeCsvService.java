package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CoffeeCSVRecord;

import java.io.File;
import java.util.List;

public interface CoffeeCsvService {
    List<CoffeeCSVRecord> convertCsv(File file);
}
