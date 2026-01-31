package guru.springframework.spring7webapp.mappers;

import guru.springframework.spring7webapp.entities.Customer;
import guru.springframework.spring7webapp.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
