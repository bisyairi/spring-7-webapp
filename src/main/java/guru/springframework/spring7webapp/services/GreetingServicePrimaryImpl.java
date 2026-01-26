package guru.springframework.spring7webapp.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class GreetingServicePrimaryImpl implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello from GreetingServicePrimaryImpl";
    }
}
