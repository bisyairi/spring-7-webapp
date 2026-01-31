package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
