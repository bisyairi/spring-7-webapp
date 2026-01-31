package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.mappers.CustomerMapper;
import guru.springframework.spring7webapp.model.CustomerDTO;
import guru.springframework.spring7webapp.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Primary
@Service
public class CustomerServiceImplJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> customerMapper.customerToCustomerDto(customer))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Integer id) {
        return Optional.ofNullable(
                customerMapper.customerToCustomerDto(customerRepository.findById(Long.valueOf(id)).orElse(null
                )));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(Integer customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(Integer customerId) {

    }

    @Override
    public void patchCustomerById(Integer customerId, CustomerDTO customerUpdates) {

    }
}
