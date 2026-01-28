package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Customer;
import guru.springframework.spring7webapp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public List<Customer> getAllCustomers(){
        log.debug("Getting all customers");
        return customerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId){
        log.debug("Getting customer by id: {}", customerId);
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<Void> createNewCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createNewCustomer(customer);
        log.debug("Creating new customer: {}", savedCustomer.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", CUSTOMER_PATH + "/" + savedCustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> updateCustomerById(@PathVariable("customerId")Integer customerId, @RequestBody Customer customer){
        customerService.updateCustomerById(customerId, customer);
        log.debug("Updating customer with id: {}", customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
        log.debug("Deleting customer with id: {}", customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<Void> partialUpdateCustomerById(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
