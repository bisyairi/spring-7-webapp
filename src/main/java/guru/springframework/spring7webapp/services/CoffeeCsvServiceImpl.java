package guru.springframework.spring7webapp.services;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring7webapp.model.CoffeeCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class CoffeeCsvServiceImpl implements CoffeeCsvService {

    @Override
    public List<CoffeeCSVRecord> convertCsv(File file) {
        try{
            List<CoffeeCSVRecord> records = new CsvToBeanBuilder<CoffeeCSVRecord>(new FileReader(file))
                    .withType(CoffeeCSVRecord.class)
                    .build()
                    .parse();

            return records;
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
