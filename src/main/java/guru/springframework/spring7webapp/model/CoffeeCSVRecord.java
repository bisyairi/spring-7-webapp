package guru.springframework.spring7webapp.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeCSVRecord {
    @CsvBindByName(column = "coffee_id")
    private Integer coffeeId;

    @CsvBindByName(column = "unit_price")
    private Float unitPrice;

    @CsvBindByName(column = "coffee_category")
    private String coffeeName;

    @CsvBindByName(column = "coffee_type")
    private String coffeeStyle;

    @CsvBindByName(column = "coffee_detail")
    private String coffeeDetail;
}
