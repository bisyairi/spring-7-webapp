package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(Integer id);

    CustomerDTO createNewCustomer(CustomerDTO customer);

    void updateCustomerById(Integer customerId, CustomerDTO customer);

    void deleteCustomerById(Integer customerId);

    void patchCustomerById(Integer customerId, CustomerDTO customerUpdates);
}
