package guru.springframework.spring7webapp.repositories;

import guru.springframework.spring7webapp.entities.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTestIT {

    @Container
    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9");

    @Autowired
    CoffeeRepository coffeeRepository;

    // the hard way
    // to get the username, password and url from the container
    // the port is randomly assigned by testcontainers
//    @DynamicPropertySource
//    static void mySqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//    }

    @Test
    void testListCoffees() {
        List<Coffee> coffees = coffeeRepository.findAll();

        assertThat(coffees.size()).isGreaterThan(0);
    }
}
