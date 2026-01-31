package guru.springframework.spring7webapp.services;

import guru.springframework.spring7webapp.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<Integer, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder().
                id(1).
                customerName("Iwan").
                version(1).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        CustomerDTO customer2 = CustomerDTO.builder().
                id(2).
                customerName("Afiq").
                version(1).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        CustomerDTO customer3 = CustomerDTO.builder().
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
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Integer id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
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
    public void updateCustomerById(Integer customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        customerMap.remove(customerId);
        log.debug("Deleted customer with id: {}", customerId);
    }

    @Override
    public void patchCustomerById(Integer customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }

        if (customer.getVersion() != null) {
            existingCustomer.setVersion(customer.getVersion());
        }
    }
}
