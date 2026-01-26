package guru.springframework.spring7webapp.services.i18n;

import guru.springframework.spring7webapp.services.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("EN")
@Service("i18NService")
public class EnglishGreetingServiceImpl implements GreetingService {

    @Override
    public String sayGreeting() {
        return "Hello from EnglishGreetingServiceImpl";
    }
}
