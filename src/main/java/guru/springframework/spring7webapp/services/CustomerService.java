package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Integer id);

    Customer createNewCustomer(Customer customer);

    void updateCustomerById(Integer customerId, Customer customer);

    void deleteCustomerById(Integer customerId);

    void patchCustomerById(Integer customerId, Customer customerUpdates);
}
