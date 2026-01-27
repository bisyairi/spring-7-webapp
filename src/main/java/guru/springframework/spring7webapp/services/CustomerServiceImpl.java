package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<Integer, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder().
                id(1).
                customerName("Iwan").
                version(1).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Customer customer2 = Customer.builder().
                id(2).
                customerName("Afiq").
                version(1).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        Customer customer3 = Customer.builder().
                id(3).
                customerName("Atan").
                version(1).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();


        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerMap.get(id);
    }

    @Override
    public Customer createNewCustomer(Customer customer) {
        Customer savedCustomer = Customer.builder()
                .id(customer.getId())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(Integer customerId, Customer customer) {
        Customer existingCustomer = customerMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customerMap.remove(customerId);
        log.debug("Deleted customer with id: {}", customerId);
    }

    @Override
    public void patchCustomerById(Integer customerId, Customer customer) {
        Customer existingCustomer = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

        if (customer.getVersion() != null) {
            existingCustomer.setVersion(customer.getVersion());
        }
    }
}
