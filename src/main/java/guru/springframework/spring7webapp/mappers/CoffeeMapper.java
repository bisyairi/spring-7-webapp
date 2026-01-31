package guru.springframework.spring7webapp.mappers;

import guru.springframework.spring7webapp.entities.Coffee;
import guru.springframework.spring7webapp.model.CoffeeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeMapper {

    Coffee coffeeDtoToCoffee(CoffeeDTO coffeeDTO);
    CoffeeDTO coffeeToCoffeeDto(Coffee coffee);
}
