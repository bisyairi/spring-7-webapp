package guru.springframework.spring7webapp.controller;

import guru.springframework.spring7webapp.model.Customer;
import guru.springframework.spring7webapp.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){
        log.debug("Getting all customers");
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId){
        log.debug("Getting customer by id: {}", customerId);
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity createNewCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createNewCustomer(customer);
        log.debug("Creating new customer: {}", savedCustomer.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable("customerId")Integer customerId, @RequestBody Customer customer){
        customerService.updateCustomerById(customerId, customer);
        log.debug("Updating customer with id: {}", customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") Integer customerId) {
        customerService.deleteCustomerById(customerId);
        log.debug("Deleting customer with id: {}", customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity partialUpdateCustomerById(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        customerService.patchCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
