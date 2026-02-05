package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoffeeRepository extends JpaRepository<Coffee, UUID> {

    Page<Coffee> findAllByCoffeeNameIsLikeIgnoreCase(String name, Pageable pageable);
}
